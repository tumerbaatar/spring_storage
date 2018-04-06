package com.github.tumerbaatar.storage.service.exceptions;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class DuplicatePartException extends RuntimeException {
    private Map<String, Object> params;
    private String message;

    public DuplicatePartException(Map<String, Object> params) {
        this.params = params;
        this.message = "";
    }

    public DuplicatePartException(Map<String, Object> params, String message) {
        this(params);
        this.message = message;
    }
}
