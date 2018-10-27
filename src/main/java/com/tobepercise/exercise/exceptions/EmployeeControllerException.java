package com.tobepercise.exercise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeControllerException extends Exception {

    public EmployeeControllerException(String message) {
        super(message);
    }
}
