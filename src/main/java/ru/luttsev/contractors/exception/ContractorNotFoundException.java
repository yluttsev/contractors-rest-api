package ru.luttsev.contractors.exception;

/**
 * Класс исключения неизвестного контрагента
 * @author Yuri Luttsev
 */
public class ContractorNotFoundException extends RuntimeException {

    public ContractorNotFoundException(String id) {
        super("Contractor with id: %s not found".formatted(id));
    }

}
