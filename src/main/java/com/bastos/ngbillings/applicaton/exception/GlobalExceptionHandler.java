package com.bastos.ngbillings.applicaton.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleAccountNotFoundException(AccountNotFoundException ex) {

        ExceptionDetails exceptionDetails = new ExceptionDetails(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                new Date()
        );

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundsBadRequestException.class)
    public ResponseEntity<ExceptionDetails> handleTransactionBadRequestException(InsufficientFundsBadRequestException ex) {

        ExceptionDetails exceptionDetails = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                new Date()
        );

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistenceAccountBadRequestException.class)
    public ResponseEntity<ExceptionDetails> handleExistenceAccountException(ExistenceAccountBadRequestException ex) {

        ExceptionDetails exceptionDetails = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                new Date()
        );

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentDeclinedBadRequestException.class)
    public ResponseEntity<ExceptionDetails> handlePaymentDeclinedException(PaymentDeclinedBadRequestException ex) {

        ExceptionDetails exceptionDetails = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                new Date()
        );

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
