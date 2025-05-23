package com.bastos.ngbillings.infra;

@FunctionalInterface
public interface PaymentGateway {

    boolean authorize(String cardNumber);
}
