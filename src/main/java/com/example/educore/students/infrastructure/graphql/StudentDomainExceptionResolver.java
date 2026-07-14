package com.example.educore.students.infrastructure.graphql;

import com.example.educore.sharedkernel.domain.DomainException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class StudentDomainExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (!(ex instanceof DomainException domainException) || !isStudentCode(domainException.getCode())) {
            return null;
        }

        ErrorType errorType = switch (domainException.getCode()) {
            case "STUDENT_IDENTIFICATION_DUPLICATE" -> ErrorType.BAD_REQUEST;
            case "STUDENT_NOT_FOUND" -> ErrorType.NOT_FOUND;
            default -> ErrorType.INTERNAL_ERROR;
        };

        return GraphqlErrorBuilder.newError(env)
                .message(domainException.getMessage())
                .errorType(errorType)
                .build();
    }

    private boolean isStudentCode(String code) {
        return code != null && code.startsWith("STUDENT_");
    }
}
