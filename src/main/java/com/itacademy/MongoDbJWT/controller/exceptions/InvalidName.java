package com.itacademy.MongoDbJWT.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class InvalidName extends Exception {


    private static final long serialVersionUID = 1L;

    public InvalidName () {
        super("The name is already picked");
    }
}
