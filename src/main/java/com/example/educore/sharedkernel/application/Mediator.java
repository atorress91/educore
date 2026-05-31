package com.example.educore.sharedkernel.application;

public interface Mediator {
    <R> R send(Command<R> command);

    <R> R ask(Query<R> query);
}
