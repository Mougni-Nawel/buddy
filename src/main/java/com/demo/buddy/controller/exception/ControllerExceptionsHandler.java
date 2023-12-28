package com.demo.buddy.controller.exception;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Generated
@Slf4j
public class ControllerExceptionsHandler {

    @ExceptionHandler(value = {NotNecessaryFundsException.class})
    public ResponseEntity<ErrorMessage> NotNecessaryFundsException(NotNecessaryFundsException e){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserException.class})
    public ResponseEntity<ErrorMessage> UserAlreadyExistException(UserException e){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_ACCEPTABLE);
    }

}
