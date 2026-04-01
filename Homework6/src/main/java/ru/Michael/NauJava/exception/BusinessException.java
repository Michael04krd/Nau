package ru.Michael.NauJava.exception;

/**
 * Базовое исключение для бизнес-ошибок.
 * Используется при нарушении бизнес-правил (например, недостаточно товара на складе).
 */

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}