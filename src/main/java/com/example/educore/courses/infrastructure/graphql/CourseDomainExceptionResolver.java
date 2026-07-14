package com.example.educore.courses.infrastructure.graphql;

import com.example.educore.sharedkernel.domain.DomainException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class CourseDomainExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (!(ex instanceof DomainException domainException) || !isCourseCode(domainException.getCode())) {
            return null;
        }

        ErrorType errorType = switch (domainException.getCode()) {
            case "COURSE_NOT_FOUND", "COURSE_NO_ACTIVE_YEAR", "COURSE_LEVEL_NOT_CONFIGURED" -> ErrorType.NOT_FOUND;
            case "COURSE_YEAR_DUPLICATE", "COURSE_ACTIVE_YEAR_EXISTS" -> ErrorType.BAD_REQUEST;
            default -> ErrorType.INTERNAL_ERROR;
        };

        return GraphqlErrorBuilder.newError(env)
                .message(domainException.getMessage())
                .errorType(errorType)
                .build();
    }

    private boolean isCourseCode(String code) {
        return code != null && code.startsWith("COURSE_");
    }
}
