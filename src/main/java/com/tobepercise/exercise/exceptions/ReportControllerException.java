package com.tobepercise.exercise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReportControllerException extends Exception {

    public ReportControllerException(String message) {
        super(message);
    }
}
