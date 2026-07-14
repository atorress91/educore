package com.example.educore.students.application.commands;

import com.example.educore.sharedkernel.application.Command;

import java.util.UUID;

public record DeleteStudentCommand(UUID studentId) implements Command<Boolean> {}
