package ru.luttsev.contractors.exception;

public class JwtTokenExpiredException extends RuntimeException {

    public JwtTokenExpiredException() {
        super("JWT token has expired.");
    }

}
