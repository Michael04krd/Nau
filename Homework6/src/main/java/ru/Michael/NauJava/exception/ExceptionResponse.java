package ru.Michael.NauJava.exception;

/**
 * DTO для представления ошибок в REST API.
 * Содержит поле {@code message} с описанием ошибки.
 */

public class ExceptionResponse {
    private String message;

    private ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ExceptionResponse create(Throwable e) {
        return new ExceptionResponse(e.getMessage());
    }

    public static ExceptionResponse create(String message) {
        return new ExceptionResponse(message);
    }
}