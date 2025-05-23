package com.bastos.ngbillings.domain.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Estratégia de taxa para transações com cartão de débito.
 * Aplica uma taxa de 3% sobre o valor original.
 *
 * @author bastos
 */
public class DebitFeeStrategy implements FeeStrategy {

    public BigDecimal apply(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(1.03)).setScale(2, RoundingMode.HALF_UP);
    }
}
