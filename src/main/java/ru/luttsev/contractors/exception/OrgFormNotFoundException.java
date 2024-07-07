package ru.luttsev.contractors.exception;

/**
 * Класс исключения неизвестной формы организации
 * @author Yuri Luttsev
 */
public class OrgFormNotFoundException extends RuntimeException {

    public OrgFormNotFoundException(Integer id) {
        super("Org form with id: %d not found".formatted(id));
    }

}
