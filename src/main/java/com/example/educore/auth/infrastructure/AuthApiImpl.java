package com.example.educore.auth.infrastructure;

import com.example.educore.auth.AuthApi;
import com.example.educore.auth.domain.model.User;
import com.example.educore.auth.domain.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class AuthApiImpl implements AuthApi {

    private final UserRepository userRepository;

    @Override
    public Optional<String> emailOf(UUID userId) {
        return userRepository.findById(userId).map(User::getEmail);
    }
}
