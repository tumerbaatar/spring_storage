package com.github.tumerbaatar.storage.service.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class PartNotFoundException extends RuntimeException {
    private Map<String, Object> params;
    private String message;

    public PartNotFoundException(Map<String, Object> params) {
        this.params = params;
        this.message = "";
    }

    public PartNotFoundException(Map<String, Object> params, String message) {
        this(params);
        this.message = message;
    }
}
