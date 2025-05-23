package com.bastos.ngbillings.infra.messaging;

import com.bastos.ngbillings.domain.model.PaymentType;

import java.math.BigDecimal;

public record TransactionEvent(String accountNumber, BigDecimal amount, PaymentType type) {}
