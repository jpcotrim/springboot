package com.example.springboot.exceptions;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public record ExceptionResponse(Date time, String message, String details) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

}
