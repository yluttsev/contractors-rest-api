package ru.luttsev.contractors.exception;

public class ContractorNotFoundException extends RuntimeException {

    public ContractorNotFoundException(String id) {
        super("Contractor with id: %s not found".formatted(id));
    }

}
