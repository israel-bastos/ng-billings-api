package com.bastos.ngbillings.adapters.controller.dto;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO para respostas de conta.
 *
 * @param accountNumber o número da conta
 * @param balance o saldo atual
 * @param transactions o conjunto de transações associadas à conta
 *
 * @author bastos
 */
public record AccountResponseDTO(
        String accountNumber,
        BigDecimal balance,
        Set<TransactionResponseDTO> transactions) {

    public AccountResponseDTO {
        if (transactions == null) {
            transactions = Set.of();
        }
    }
}
