package com.bastos.ngbillings.applicaton.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() { super("Account not found" );}
}
