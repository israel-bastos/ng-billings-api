package com.bastos.ngbillings.domain.strategy;

import com.bastos.ngbillings.domain.model.PaymentType;

/**
 * Fábrica responsável por instanciar a estratégia de taxa apropriada
 * com base no tipo de pagamento informado.
 *
 * Utiliza o padrão Strategy para delegar o cálculo de taxa.
 *
 * @author bastos
 */
public class FeeStrategyFactory {

    private FeeStrategyFactory() {}

    public static FeeStrategy get(PaymentType type) {

        return switch (type) {
            case D -> new DebitFeeStrategy();
            case C -> new CreditFeeStrategy();
            default -> new PixFeeStrategy();
        };
    }
}
