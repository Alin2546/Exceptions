package org.example;

public class NegativeAgeException extends RuntimeException {
    public NegativeAgeException(String message) {
        super(message);
    }
}
