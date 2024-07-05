package ru.luttsev.contractors.exception;

public class OrgFormNotFoundException extends RuntimeException {

    public OrgFormNotFoundException(Integer id) {
        super("Org form with id: %d not found".formatted(id));
    }

}
