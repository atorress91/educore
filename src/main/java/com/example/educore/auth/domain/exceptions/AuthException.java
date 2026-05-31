package com.example.educore.auth.domain.exceptions;

import com.example.educore.sharedkernel.domain.DomainException;

public class AuthException extends DomainException {

    private AuthException(String code, String message) {
        super(code, message);
    }

    public static AuthException emailAlreadyExists(String email) {
        return new AuthException("AUTH_EMAIL_DUPLICATE", "El email '" + email + "' ya esta registrado");
    }

    public static AuthException invalidCredentials() {
        return new AuthException("AUTH_INVALID_CREDENTIALS", "Email o contrasena incorrectos");
    }

    public static AuthException userNotFound() {
        return new AuthException("AUTH_USER_NOT_FOUND", "Usuario no encontrado");
    }

    public static AuthException notAuthenticated() {
        return new AuthException("AUTH_NOT_AUTHENTICATED", "Debe iniciar sesion para acceder a este recurso");
    }
}
