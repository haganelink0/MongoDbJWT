package com.itacademy.MongoDbJWT.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class HistoryException extends Exception {
    private static final long serialVersionUID = 1L;

    public HistoryException() {
        super("Game History already empty!");
    }
}