package com.bastos.ngbillings.adapters.controller.dto;

import java.math.BigDecimal;

/**
 * DTO para requisições de criação de conta.
 *
 * @param accountNumber o número da conta
 * @param balance o saldo inicial
 *
 * @author bastos
 */
public record AccountRequestDTO(
        String accountNumber,
        BigDecimal balance
) {}
