package com.bastos.ngbillings.usecase;

import com.bastos.ngbillings.adapters.controller.dto.AccountRequestDTO;
import com.bastos.ngbillings.adapters.psersistence.entity.AccountEntity;
import com.bastos.ngbillings.adapters.psersistence.repository.AccountRepository;
import com.bastos.ngbillings.applicaton.service.CreateAccountService;
import com.bastos.ngbillings.applicaton.exception.ExistenceAccountBadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateAccountServiceTest {

    private final AccountRepository repository = mock(AccountRepository.class);
    private final CreateAccountService service = new CreateAccountService(repository);

    @Test
    @DisplayName("should create a new account")
    void shouldCreateNewAccount() {
        var request = new AccountRequestDTO("acc-001", new BigDecimal("100.00"));

        when(repository.findByAccountNumber("acc-001")).thenReturn(Optional.empty());
        when(repository.save(any(AccountEntity.class))).thenAnswer(i -> i.getArgument(0));

        var result = service.execute(request);

        assertEquals("acc-001", result.accountNumber());
        assertEquals(new BigDecimal("100.00"), result.balance());
    }

    @Test
    @DisplayName("should throw exception when account number exists")
    void shouldThrowExceptionWhenAccountAlreadyExists() {
        var request = new AccountRequestDTO("acc-001", new BigDecimal("100.00"));

        when(repository.existsByAccountNumber("acc-001"))
                .thenReturn(true);

        assertThrows(ExistenceAccountBadRequestException.class, () -> service.execute(request));
    }
}
