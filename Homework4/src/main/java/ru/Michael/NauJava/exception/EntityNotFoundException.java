package ru.Michael.NauJava.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " с id " + id + " не найден");
    }

    public EntityNotFoundException(String entityName, String identifier) {
        super(entityName + " с идентификатором '" + identifier + "' не найден");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}