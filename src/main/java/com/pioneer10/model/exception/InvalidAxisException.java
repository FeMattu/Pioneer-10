package com.pioneer10.model.exception;

public class InvalidAxisException extends Throwable {
    public InvalidAxisException(char axis) {
        super("Invalid axis entered: axis, this should be X, Y or Z");
    }
}
