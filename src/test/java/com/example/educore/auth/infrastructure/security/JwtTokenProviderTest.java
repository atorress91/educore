package com.example.educore.auth.infrastructure.security;

import com.example.educore.auth.domain.model.Role;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.security.TokenClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private static final String DEV_SECRET = "ZWR1Y29yZS1zZWNyZXQta2V5LWZvci1kZXZlbG9wbWVudC1vbmx5LWNoYW5nZS1pbi1wcm9kdWN0aW9u";

    private JwtTokenProvider provider;

    @BeforeEach
    void setUp() {
        provider = buildProvider(DEV_SECRET, 60_000L);
    }

    @Test
    void issued_token_round_trips_user_id_and_role() {
        User user = User.register("Ada", "Lovelace", "1234567", "ada@example.com", "hash", Role.TEACHER);

        String token = provider.issueFor(user);
        Optional<TokenClaims> claims = provider.validate(token);

        assertThat(claims).isPresent();
        assertThat(claims.get().userId()).isEqualTo(user.getId());
        assertThat(claims.get().role()).isEqualTo("TEACHER");
    }

    @Test
    void invalid_token_returns_empty() {
        assertThat(provider.validate("not-a-token")).isEmpty();
    }

    @Test
    void token_signed_with_other_key_returns_empty() {
        JwtTokenProvider other = buildProvider(
                "b3RoZXItc2VjcmV0LWtleS1mb3ItdGVzdGluZy1vbmx5LXVzZS1kaWZmZXJlbnQtdmFsdWU=", 60_000L);
        User user = User.register("X", "Y", "9999999", "x@y.com", "h", Role.STUDENT);
        String foreignToken = other.issueFor(user);

        assertThat(provider.validate(foreignToken)).isEmpty();
    }

    @Test
    void expired_token_returns_empty() throws InterruptedException {
        JwtTokenProvider shortLived = buildProvider(DEV_SECRET, 1L);
        User user = User.register("X", "Y", "9999999", "x@y.com", "h", Role.STUDENT);
        String token = shortLived.issueFor(user);
        Thread.sleep(20);

        assertThat(shortLived.validate(token)).isEmpty();
    }

    @Test
    void claims_user_id_matches_aggregate_id() {
        UUID expectedId = UUID.randomUUID();
        User user = User.register("X", "Y", "9999999", "x@y.com", "h", Role.STUDENT);
        ReflectionTestUtils.setField(user, "id", expectedId);

        String token = provider.issueFor(user);

        assertThat(provider.validate(token))
                .map(TokenClaims::userId)
                .contains(expectedId);
    }

    private JwtTokenProvider buildProvider(String secret, long expirationMs) {
        JwtTokenProvider p = new JwtTokenProvider();
        ReflectionTestUtils.setField(p, "secret", secret);
        ReflectionTestUtils.setField(p, "expirationMs", expirationMs);
        p.init();
        return p;
    }
}
