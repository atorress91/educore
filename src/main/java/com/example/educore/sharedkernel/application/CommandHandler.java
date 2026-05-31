package com.example.educore.sharedkernel.application;

public interface CommandHandler<C, R> {
    R handle(C command);
}
