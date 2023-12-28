package com.demo.buddy.controller.exception;

import lombok.Generated;
import lombok.Getter;

import java.util.Date;

@Generated
@Getter
public class ErrorMessage {

    private int statusCode;
    private Date timestamp;
    private String message;

    public ErrorMessage(int statusCode, Date timestamp, String message) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
    }

}

