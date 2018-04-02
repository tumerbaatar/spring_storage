package com.github.tumerbaatar.storage.service.exceptions;

public class BoxNotFoundException extends RuntimeException {
    public BoxNotFoundException(String message) {
        super(message);
    }

    public BoxNotFoundException() {
        super();
    }
}
