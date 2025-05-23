package com.bastos.ngbillings.applicaton.exception;

public class ExistenceAccountBadRequestException extends RuntimeException {

    public ExistenceAccountBadRequestException() { super("Account already exists"); }
}
