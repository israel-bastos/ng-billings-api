package com.bastos.ngbillings.domain.vo;

import com.bastos.ngbillings.domain.model.PaymentType;
import com.bastos.ngbillings.domain.strategy.FeeStrategy;
import com.bastos.ngbillings.domain.strategy.FeeStrategyFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Objeto de Valor (Value Object) que representa uma transação financeira.
 *
 * Este VO encapsula o valor original da transação e o tipo de pagamento associado (D, C, P).
 * Fornece o cálculo do valor com taxa aplicando a estratégia correspondente ao tipo de pagamento.
 *
 * O valor da transação é sempre armazenado com duas casas decimais de precisão.
 *
 * @author bastos
 */
public class TransactionVO {

    private final BigDecimal originalAmount;
    private final PaymentType paymentType;

    public TransactionVO(BigDecimal originalAmount, PaymentType paymentType) {
        this.originalAmount = originalAmount.setScale(2, RoundingMode.HALF_UP);
        this.paymentType = paymentType;
    }

    public BigDecimal withFee() {
        FeeStrategy strategy = FeeStrategyFactory.get(paymentType);
        return strategy.apply(originalAmount);
    }
}
