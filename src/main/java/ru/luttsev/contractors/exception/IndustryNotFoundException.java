package ru.luttsev.contractors.exception;

public class IndustryNotFoundException extends RuntimeException {

    public IndustryNotFoundException(Integer industryId) {
        super("Industry with id: %d not found".formatted(industryId));
    }

}
