package ru.luttsev.contractors.exception;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(String countryId) {
        super("Country with id: %s not found".formatted(countryId));
    }

}
