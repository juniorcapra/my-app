package com.mycompany.app.myapp.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ClientNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(ClientNotFoundException ex,
        WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
            request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientIsPresentException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(ClientIsPresentException ex,
        WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
            request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.FOUND);
    }

}
