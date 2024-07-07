package ru.luttsev.contractors.exception;

/**
 * Класс исключения неизвестной промышленности
 * @author Yuri Luttsev
 */
public class IndustryNotFoundException extends RuntimeException {

    public IndustryNotFoundException(Integer industryId) {
        super("Industry with id: %d not found".formatted(industryId));
    }

}
