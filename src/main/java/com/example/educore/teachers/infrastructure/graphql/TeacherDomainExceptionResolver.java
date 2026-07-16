package com.example.educore.teachers.infrastructure.graphql;

import com.example.educore.sharedkernel.domain.DomainException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class TeacherDomainExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (!(ex instanceof DomainException domainException) || !isTeacherCode(domainException.getCode())) {
            return null;
        }

        ErrorType errorType = switch (domainException.getCode()) {
            case "TEACHER_INVALID_SUBJECT", "TEACHER_INVALID_SECTION" -> ErrorType.BAD_REQUEST;
            case "TEACHER_NOT_FOUND" -> ErrorType.NOT_FOUND;
            default -> ErrorType.INTERNAL_ERROR;
        };

        return GraphqlErrorBuilder.newError(env)
                .message(domainException.getMessage())
                .errorType(errorType)
                .build();
    }

    private boolean isTeacherCode(String code) {
        return code != null && code.startsWith("TEACHER_");
    }
}
