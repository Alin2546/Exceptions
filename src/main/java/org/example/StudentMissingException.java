package org.example;

public class StudentMissingException extends RuntimeException {
    public StudentMissingException(String message) {
        super(message);
    }
}
