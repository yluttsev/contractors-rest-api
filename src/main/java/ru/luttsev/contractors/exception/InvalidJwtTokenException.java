package ru.luttsev.contractors.exception;

public class InvalidJwtTokenException extends RuntimeException {

    public InvalidJwtTokenException() {
        super("Invalid JWT token.");
    }

}
