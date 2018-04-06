package com.github.tumerbaatar.storage.web.controller.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int code;
    private String message;
    private Map<String, Object> params;
}
