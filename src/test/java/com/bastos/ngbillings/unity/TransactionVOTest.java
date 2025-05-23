package com.bastos.ngbillings.unity;

import com.bastos.ngbillings.domain.model.PaymentType;
import com.bastos.ngbillings.domain.vo.TransactionVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionVOTest {

    private static final BigDecimal PIX_AMOUNT = new BigDecimal("100.00");
    private static final BigDecimal DEBIT_CARD_AMOUNT = new BigDecimal("103.00");
    private static final BigDecimal CREDIT_CARD_AMOUNT = new BigDecimal("105.00");

    private static BigDecimal rounding(BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Test
    @DisplayName("should return original amount for pix - must assure not to apply fee")
    void shouldReturnOriginalAmountForPix() {
        TransactionVO vo = new TransactionVO(new BigDecimal("100.00"), PaymentType.P);
        assertEquals(rounding(PIX_AMOUNT), vo.withFee());
    }

    @Test
    @DisplayName("should apply 3% for debt card")
    void shouldApply3PercentFeeForDebitCard() {
        TransactionVO vo = new TransactionVO(new BigDecimal("100.00"), PaymentType.D);
        BigDecimal a = rounding(DEBIT_CARD_AMOUNT);

        assertEquals(a, vo.withFee());
    }

    @Test
    @DisplayName("should apply 5% for credit card")
    void shouldApply5PercentFeeForCreditCard() {
        TransactionVO vo = new TransactionVO(new BigDecimal("100.00"), PaymentType.C);
        assertEquals(rounding(CREDIT_CARD_AMOUNT), vo.withFee());
    }
}
