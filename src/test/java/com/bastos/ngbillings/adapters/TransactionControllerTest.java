package com.bastos.ngbillings.adapters;

import com.bastos.ngbillings.adapters.controller.TransactionController;
import com.bastos.ngbillings.adapters.controller.dto.TransactionRequestDTO;
import com.bastos.ngbillings.adapters.controller.dto.TransactionResponseDTO;
import com.bastos.ngbillings.applicaton.exception.AccountNotFoundException;
import com.bastos.ngbillings.applicaton.exception.InsufficientFundsBadRequestException;
import com.bastos.ngbillings.applicaton.exception.PaymentDeclinedBadRequestException;
import com.bastos.ngbillings.applicaton.usecase.CreateTransactionUsecase;
import com.bastos.ngbillings.domain.model.PaymentType;
import com.bastos.ngbillings.infra.PaymentGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateTransactionUsecase createTransactionUsecase;

    @MockBean
    private PaymentGateway paymentGateway;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should return 200 when account is created successfully")
    void shouldCreateTransactionSuccessfully() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO(
                "acc-001",
                new BigDecimal("20.00"),
                PaymentType.D
        );

        TransactionResponseDTO response = new TransactionResponseDTO(new BigDecimal("80.00"), PaymentType.D);

        when(createTransactionUsecase.execute(request)).thenReturn(response);

        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(80.00))
                .andExpect(jsonPath("$.type").value("D"));
    }

    @Test
    @DisplayName("should return 400 when balance is insufficient")
    void shouldReturnBadRequestWhenInsufficientFunds() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO(
                "acc-001",
                new BigDecimal("1000.00"),
                PaymentType.C
        );

        when(createTransactionUsecase.execute(request))
                .thenThrow(new InsufficientFundsBadRequestException());

        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 404 when account does not exist")
    void shouldReturnBadRequestWhenAccountDoesNotExist() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO(
                "acc-000",
                new BigDecimal("20.00"),
                PaymentType.D
        );

        when(createTransactionUsecase.execute(request))
                .thenThrow(new AccountNotFoundException());

        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return 400 when payment is declined")
    void shouldReturnBadRequestWhenPaymentDeclined() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO(
                "acc-001",
                new BigDecimal("20.00"),
                PaymentType.C
        );

        when(createTransactionUsecase.execute(request))
                .thenThrow(new PaymentDeclinedBadRequestException());

        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
