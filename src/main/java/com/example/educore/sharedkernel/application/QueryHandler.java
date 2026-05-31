package com.example.educore.sharedkernel.application;

public interface QueryHandler<Q extends Query<R>, R> {
    R handle(Q query);
}
