package com.example.educore.students.application.queries;

import com.example.educore.sharedkernel.application.Query;
import com.example.educore.students.domain.model.Level;
import com.example.educore.students.domain.model.Subject;

import java.util.List;

public record GetCurriculumQuery(Level level) implements Query<List<Subject>> {}
