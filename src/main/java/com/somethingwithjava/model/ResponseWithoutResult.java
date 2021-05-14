package com.somethingwithjava.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWithoutResult {
    private int httpCode;
    private String httpMessage;
    private String message;

    public ResponseWithoutResult(int httpCode, String httpMessage, String message) {
        this.httpCode = httpCode;
        this.httpMessage = httpMessage;
        this.message = message;
    }
}
