package com.bastos.ngbillings.domain.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Estratégia de taxa para transações com cartão de crédito.
 * Aplica uma taxa de 5% sobre o valor original.
 *
 * @author bastos
 */
public class CreditFeeStrategy implements FeeStrategy {

    public BigDecimal apply(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(1.05)).setScale(2, RoundingMode.HALF_UP);
    }
}
