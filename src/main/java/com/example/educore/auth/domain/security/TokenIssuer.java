package com.example.educore.auth.domain.security;

import com.example.educore.auth.domain.model.User;

public interface TokenIssuer {
    String issueFor(User user);
}
