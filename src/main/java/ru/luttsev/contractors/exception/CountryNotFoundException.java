package ru.luttsev.contractors.exception;

/**
 * Класс исключения неизвестной страны
 * @author Yuri Luttsev
 */
public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(String countryId) {
        super("Country with id: %s not found".formatted(countryId));
    }

}
