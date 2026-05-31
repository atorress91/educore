package com.example.educore.sharedkernel.infrastructure.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.jspecify.annotations.NonNull;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SecurityExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(@NonNull Throwable ex, @NonNull DataFetchingEnvironment env) {
        if (!(ex instanceof AccessDeniedException) && !(ex instanceof AuthenticationException)) {
            return null;
        }

        return GraphqlErrorBuilder.newError(env)
                .message("No autorizado")
                .errorType(ErrorType.UNAUTHORIZED)
                .build();
    }
}
