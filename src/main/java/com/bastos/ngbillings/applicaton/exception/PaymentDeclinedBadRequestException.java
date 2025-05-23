package com.bastos.ngbillings.applicaton.exception;

public class PaymentDeclinedBadRequestException extends RuntimeException {

    public PaymentDeclinedBadRequestException() { super("Payment Reject"); }
}
