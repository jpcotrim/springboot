package com.example.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedParamsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -8278860188594539374L;

    public UnsupportedParamsException(String message) {
        super(message);
    }
}
