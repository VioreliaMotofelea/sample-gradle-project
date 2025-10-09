package com.example.validator;

import java.util.List;

public class ValidationException extends Exception {
    private final List<String> errors;

    public ValidationException(List<String> errors) {
        super(String.join("; ", errors)); // opțional, ca mesaj
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
