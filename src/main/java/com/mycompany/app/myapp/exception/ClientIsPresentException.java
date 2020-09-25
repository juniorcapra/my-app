package com.mycompany.app.myapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class ClientIsPresentException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ClientIsPresentException(String message) {
        super(message);
    }

}
