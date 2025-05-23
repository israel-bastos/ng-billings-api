package com.bastos.ngbillings.usecase;

import com.bastos.ngbillings.adapters.psersistence.entity.AccountEntity;
import com.bastos.ngbillings.adapters.psersistence.entity.TransactionEntity;
import com.bastos.ngbillings.adapters.psersistence.repository.AccountRepository;
import com.bastos.ngbillings.applicaton.service.FindAccountService;
import com.bastos.ngbillings.domain.model.PaymentType;
import com.bastos.ngbillings.applicaton.exception.AccountNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private FindAccountService service;

    @Test
    @DisplayName("should return account without transactions")
    void shouldReturnAccountWithTransactions() {
        AccountEntity account = new AccountEntity("acc-001", new BigDecimal("100.00"));

        Set<TransactionEntity> transactions = Set.of(
                new TransactionEntity(account, PaymentType.D, new BigDecimal("10.00")),
                new TransactionEntity(account, PaymentType.C, new BigDecimal("20.00"))
        );

        try {
            var field = AccountEntity.class.getDeclaredField("transactions");
            field.setAccessible(true);
            field.set(account, transactions);
        } catch (Exception e) {
            fail("Não foi possível injetar transações: " + e.getMessage());
        }

        when(accountRepository.findByAccountNumber("acc-001")).thenReturn(Optional.of(account));

        var response = service.execute("acc-001");

        assertEquals("acc-001", response.accountNumber());
        assertEquals(new BigDecimal("100.00"), response.balance());
        assertEquals(2, response.transactions().size());
    }

    @Test
    @DisplayName("should return account without transactions")
    void shouldThrowWhenAccountNotFound() {
        when(accountRepository.findByAccountNumber("acc-000")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.execute("acc-000"));
    }
}
