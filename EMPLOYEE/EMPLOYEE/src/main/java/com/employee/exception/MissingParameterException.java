package com.employee.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class MissingParameterException extends RuntimeException {

    private String message;
    private HttpStatus status;
    public MissingParameterException(String message) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
