package com.itacademy.MongoDbJWT.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidJSON extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidJSON () {

        super("Invalid data on Json");
    }

    public InvalidJSON (String reason) {
        super(reason);}
}
