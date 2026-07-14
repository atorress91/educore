package com.example.educore.sharedkernel.domain;

/**
 * Academic level (7th–11th grade of the night high school). Shared kernel:
 * both the students and courses modules agree on this concept.
 */
public enum Level {
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11);

    private final int number;

    Level(int number) {
        this.number = number;
    }

    public int number() {
        return number;
    }
}
