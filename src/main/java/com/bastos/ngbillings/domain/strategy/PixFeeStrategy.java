package com.bastos.ngbillings.domain.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Estratégia de taxa para transações via Pix.
 * Não aplica nenhuma taxa adicional sobre o valor original.
 *
 * @author bastos
 */
public class PixFeeStrategy implements FeeStrategy {

    public BigDecimal apply(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }
}
