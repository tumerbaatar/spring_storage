package org.github.tumerbaatar.storage.service.exceptions;

public class PartNotFoundException extends RuntimeException {
    public PartNotFoundException() {
        super();
    }

    public PartNotFoundException(String message) {
        super(message);
    }
}
