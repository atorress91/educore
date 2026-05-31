package com.example.educore.sharedkernel.application;

public interface CommandHandler<C extends Command<R>, R> {
    R handle(C command);
}
