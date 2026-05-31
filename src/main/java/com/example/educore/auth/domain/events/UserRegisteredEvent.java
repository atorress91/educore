package com.example.educore.auth.domain.events;

import com.example.educore.sharedkernel.domain.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record UserRegisteredEvent(UUID eventId, Instant occurredOn, UUID userId, String email) implements DomainEvent {

    public UserRegisteredEvent(UUID userId, String email) {
        this(UUID.randomUUID(), Instant.now(), userId, email);
    }
}
