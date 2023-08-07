package com.example.springboot.exceptions.handler;

import com.example.springboot.exceptions.ExceptionResponse;
import com.example.springboot.exceptions.ResourceNotFoundException;
import com.example.springboot.exceptions.UnsupportedParamsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LogManager.getLogger(CustomizedResponseEntityExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error(ex.getMessage());
        return generateExceptionResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedParamsException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception ex, WebRequest request) {
        logger.info(ex.getMessage());
        return generateExceptionResponseEntity(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest request) {
        logger.info(ex.getMessage());
        return generateExceptionResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ExceptionResponse> generateExceptionResponseEntity(
            Exception ex, WebRequest request, HttpStatus status) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, status);
    }

}
