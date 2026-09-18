package com.collegeevent.exception;

public class DuplicateRegistrationException extends Exception {
    public DuplicateRegistrationException(String message) {
        super(message);
    }
}