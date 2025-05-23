package com.bastos.ngbillings.adapters.controller.dto;

import com.bastos.ngbillings.domain.model.PaymentType;

import java.math.BigDecimal;

/**
 * DTO para respostas de transação.
 *
         * @param amount o valor da transação
 * @param type o tipo de pagamento
 *
 * @author bastos
 */
public record TransactionResponseDTO(
        BigDecimal amount,
        PaymentType type) {
}
