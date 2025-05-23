package com.bastos.ngbillings.adapters.controller.dto;

import com.bastos.ngbillings.domain.model.PaymentType;
import java.math.BigDecimal;

/**
 * DTO para requisições de transação.
 *
 * @param accountNumber o número da conta
 * @param amount o valor da transação
 * @param type o tipo de pagamento
 *
 * @author bastos
 */
public record TransactionRequestDTO(
        String accountNumber,
        BigDecimal amount,
        PaymentType type) {
}
