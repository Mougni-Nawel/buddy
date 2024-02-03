package com.demo.buddy.controller.exception;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Objects;

@ControllerAdvice
@Generated
@Slf4j
public class ControllerExceptionsHandler {

    @ExceptionHandler(value = {NotNecessaryFundsException.class})
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotNecessaryFundsException(NotNecessaryFundsException e, RedirectAttributes redirectAttributes){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

        return "redirect:/home";
    }

    @ExceptionHandler(value = {UserException.class})
    public String UserAlreadyExistException(UserException e, RedirectAttributes redirectAttributes){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

        if (Objects.equals(e.getMessage(), "This user already exists")) {
            return "redirect:/signup";
        }
        return "redirect:/home";
    }

}
