package com.bastos.ngbillings.applicaton.exception;

public class InsufficientFundsBadRequestException extends RuntimeException {

    public InsufficientFundsBadRequestException() { super("Transaction bad request"); }
}
