package ru.Michael.NauJava.exception;

/**
 * Исключение, возникающее при попытке получить несуществующую сущность.
 * Наследуется от {@link BusinessException} для единой обработки.
 */

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