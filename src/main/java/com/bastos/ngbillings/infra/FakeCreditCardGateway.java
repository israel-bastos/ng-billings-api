package com.bastos.ngbillings.infra;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class FakeCreditCardGateway implements PaymentGateway {

    private static final Logger log = LoggerFactory.getLogger(FakeCreditCardGateway.class);

    @Override
    public boolean authorize(String cardNumber) {
        boolean isAuthorized = cardNumber.endsWith("C");

        log.info("[GATEWAY SIMULADO] Cartão {} autorizado? {}", cardNumber, isAuthorized);

        return isAuthorized;
    }
}
