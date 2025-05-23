package com.bastos.ngbillings.infra.messaging;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class FakeTransactionQueuePublisher implements TransactionQueuePublisher {

    private static final Logger log = LoggerFactory.getLogger(FakeTransactionQueuePublisher.class);

    @Override
    public void publish(String payload) {
        log.info("[FAKE QUEUE] Evento publicado: " + payload);
    }
}
