package com.example.educore.students.application.queries;

import com.example.educore.sharedkernel.application.QueryHandler;
import com.example.educore.students.domain.model.Curriculum;
import com.example.educore.students.domain.model.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCurriculumQueryHandler implements QueryHandler<GetCurriculumQuery, List<Subject>> {

    @Override
    public List<Subject> handle(GetCurriculumQuery query) {
        return Curriculum.forLevel(query.level());
    }
}
