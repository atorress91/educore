package com.example.educore.sharedkernel.application;

public interface QueryHandler<Q, R> {
    R handle(Q query);
}
