package com.example.educore.teachers.application.commands;

import com.example.educore.sharedkernel.application.Command;

import java.util.UUID;

public record DeleteTeacherCommand(UUID teacherId) implements Command<Boolean> {}
