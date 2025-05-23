package com.bastos.ngbillings.adapters;

import com.bastos.ngbillings.adapters.controller.AccountController;
import com.bastos.ngbillings.adapters.controller.dto.AccountRequestDTO;
import com.bastos.ngbillings.adapters.controller.dto.AccountResponseDTO;
import com.bastos.ngbillings.adapters.controller.dto.TransactionResponseDTO;
import com.bastos.ngbillings.applicaton.exception.ExistenceAccountBadRequestException;
import com.bastos.ngbillings.applicaton.usecase.CreateAccountUsecase;
import com.bastos.ngbillings.applicaton.usecase.FindAccountUsecase;
import com.bastos.ngbillings.domain.model.PaymentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAccountUsecase createAccountUsecase;

    @MockBean
    private FindAccountUsecase findAccountUsecase;

    @Test
    @DisplayName("should create account")
    void shouldCreateAccount() throws Exception {
        var request = new AccountRequestDTO("acc-001", new BigDecimal("100.00"));
        var response = new AccountResponseDTO("acc-001", new BigDecimal("100.00"), null);

        when(createAccountUsecase.execute(request)).thenReturn(response);

        mockMvc.perform(post("/v1/accounts")
                        .contentType("application/json")
                        .content("{\"accountNumber\":\"acc-001\",\"balance\":100.00}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").value("acc-001"))
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    @DisplayName("should return account with transactions")
    void shouldReturnAccountWithTransactions() throws Exception {
        var response = new AccountResponseDTO("acc-001", new BigDecimal("150.00"), Set.of(
                new TransactionResponseDTO(new BigDecimal("50.00"), PaymentType.D),
                new TransactionResponseDTO(new BigDecimal("25.00"), PaymentType.C)
        ));

        when(findAccountUsecase.execute("acc-001")).thenReturn(response);

        mockMvc.perform(get("/v1/accounts/acc-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("acc-001"))
                .andExpect(jsonPath("$.balance").value(150.00))
                .andExpect(jsonPath("$.transactions.length()").value(2));
    }

    @Test
    @DisplayName("should return 400 when account already exists")
    void shouldReturnBadRequestWhenAccountAlreadyExists() throws Exception {

        when(createAccountUsecase.execute(any()))
                .thenThrow(new ExistenceAccountBadRequestException());

        mockMvc.perform(post("/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"acc-001\",\"balance\":100.00}"))
                .andExpect(status().isBadRequest());
    }
}
