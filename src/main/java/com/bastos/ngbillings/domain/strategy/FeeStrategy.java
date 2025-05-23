package com.bastos.ngbillings.domain.strategy;

import java.math.BigDecimal;

/**
 * Interface que define o contrato para aplicação de taxa sobre um valor monetário.
 * Implementações desta interface representam estratégias específicas de cálculo de taxa.
 *
 * @author bastos
 */
public interface FeeStrategy {

    BigDecimal apply(BigDecimal amount);
}
