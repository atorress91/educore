package com.example.educore.sharedkernel.infrastructure.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ValidationExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (!(ex instanceof ConstraintViolationException violation)) {
            return null;
        }

        String message = violation.getConstraintViolations().stream()
                .map(this::format)
                .collect(Collectors.joining("; "));

        return GraphqlErrorBuilder.newError(env)
                .message(message.isEmpty() ? "Datos invalidos" : message)
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }

    private String format(ConstraintViolation<?> v) {
        return v.getPropertyPath() + ": " + v.getMessage();
    }
}
