package com.bastos.ngbillings.usecase;

import com.bastos.ngbillings.adapters.controller.dto.TransactionRequestDTO;
import com.bastos.ngbillings.adapters.controller.dto.TransactionResponseDTO;
import com.bastos.ngbillings.adapters.psersistence.entity.AccountEntity;
import com.bastos.ngbillings.adapters.psersistence.entity.TransactionEntity;
import com.bastos.ngbillings.adapters.psersistence.repository.AccountRepository;
import com.bastos.ngbillings.adapters.psersistence.repository.TransactionRepository;
import com.bastos.ngbillings.applicaton.exception.AccountNotFoundException;
import com.bastos.ngbillings.applicaton.exception.InsufficientFundsBadRequestException;
import com.bastos.ngbillings.applicaton.exception.PaymentDeclinedBadRequestException;
import com.bastos.ngbillings.applicaton.service.CreateTransactionService;
import com.bastos.ngbillings.domain.model.PaymentType;
import com.bastos.ngbillings.infra.PaymentGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    PaymentGateway paymentGateway;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private CreateTransactionService service;

    @Test
    @DisplayName("should create transaction successfully")
    void shouldCreateTransactionSuccessfully() {
        var request = new TransactionRequestDTO("acc-001", new BigDecimal("50.00"), PaymentType.D);
        var account = new AccountEntity("acc-001", new BigDecimal("100.00"));

        when(accountRepository.findByAccountNumber("acc-001"))
                .thenReturn(Optional.of(account));

        TransactionResponseDTO response = service.execute(request);

        assertEquals(new BigDecimal("50.00"), response.amount());
        assertEquals(PaymentType.D, response.type());
        verify(transactionRepository).save(any(TransactionEntity.class));
        verify(accountRepository).save(account);
    }

    @Test
    @DisplayName("should throw InsufficientFundsException")
    void shouldThrowInsufficientFundsException() {
        var request = new TransactionRequestDTO("acc-001", new BigDecimal("1000.00"), PaymentType.C);
        var account = new AccountEntity("acc-001", new BigDecimal("100.00"));

        when(accountRepository.findByAccountNumber("acc-001"))
                .thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsBadRequestException.class, () -> service.execute(request));
    }

    @Test
    @DisplayName("should throw AccountNotFoundException")
    void shouldThrowAccountNotFoundException() {
        var request = new TransactionRequestDTO("acc-000", new BigDecimal("50.00"), PaymentType.P);

        when(accountRepository.findByAccountNumber("acc-000"))
                .thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.execute(request));
    }

    @Test
    @DisplayName("should throw PaymentDeclinedBadRequestException when credit card is declined")
    void shouldThrowPaymentDeclinedException() {
        TransactionRequestDTO request = new TransactionRequestDTO(
                "acc-001",
                new BigDecimal("100.00"),
                PaymentType.C
        );

        AccountEntity account = new AccountEntity();
        account.setAccountNumber("acc-001");
        account.setBalance(new BigDecimal("500.00"));

        when(accountRepository.findByAccountNumber("acc-001"))
                .thenReturn(Optional.of(account));

        when(paymentGateway.authorize(anyString()))
                .thenReturn(false);

        assertThrows(PaymentDeclinedBadRequestException.class, () -> service.execute(request));
    }
}
