package com.galvanize.gmdb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class StarNeededException extends RuntimeException {
    public StarNeededException(String message) {
        super(message);
    }
}
