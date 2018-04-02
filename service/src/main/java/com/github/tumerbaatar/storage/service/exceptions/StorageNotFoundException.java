package com.github.tumerbaatar.storage.service.exceptions;

public class StorageNotFoundException extends RuntimeException {
    public StorageNotFoundException(String message) {
        super(message);
    }

    public StorageNotFoundException() {
        super();
    }
}
