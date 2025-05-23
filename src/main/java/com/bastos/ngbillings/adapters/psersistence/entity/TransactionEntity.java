package com.bastos.ngbillings.adapters.psersistence.entity;

import com.bastos.ngbillings.domain.model.PaymentType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType PaymentType;

    private BigDecimal Amount;

    public TransactionEntity() {}

    public TransactionEntity(AccountEntity account, PaymentType paymentType, BigDecimal amount) {
        this.account = account;
        PaymentType = paymentType;
        Amount = amount;
    }

    public PaymentType getPaymentType() {
        return PaymentType;
    }

    public BigDecimal getAmount() {
        return Amount;
    }
}
