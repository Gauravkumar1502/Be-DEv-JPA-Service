package dev.gaurav.testjpa.exceptions;

public class DoesNotExistException extends RuntimeException {
    public DoesNotExistException(String message) {
        super(message);
    }
}
