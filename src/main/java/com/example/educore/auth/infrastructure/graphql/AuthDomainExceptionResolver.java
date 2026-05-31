package com.example.educore.auth.infrastructure.graphql;

import com.example.educore.sharedkernel.domain.DomainException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class AuthDomainExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (!(ex instanceof DomainException domainException) || !isAuthCode(domainException.getCode())) {
            return null;
        }

        ErrorType errorType = switch (domainException.getCode()) {
            case "AUTH_INVALID_CREDENTIALS", "AUTH_NOT_AUTHENTICATED" -> ErrorType.UNAUTHORIZED;
            case "AUTH_EMAIL_DUPLICATE" -> ErrorType.BAD_REQUEST;
            case "AUTH_USER_NOT_FOUND" -> ErrorType.NOT_FOUND;
            default -> ErrorType.INTERNAL_ERROR;
        };

        return GraphqlErrorBuilder.newError(env)
                .message(domainException.getMessage())
                .errorType(errorType)
                .build();
    }

    private boolean isAuthCode(String code) {
        return code != null && code.startsWith("AUTH_");
    }
}
