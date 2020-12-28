package ru.digitalhabbits.homework3.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
