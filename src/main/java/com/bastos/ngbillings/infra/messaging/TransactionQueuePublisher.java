package com.bastos.ngbillings.infra.messaging;

public interface TransactionQueuePublisher {

    void publish(String payload);
}
