package com.bastos.ngbillings.integration;

import com.bastos.ngbillings.adapters.controller.dto.TransactionRequestDTO;
import com.bastos.ngbillings.domain.model.PaymentType;
import com.bastos.ngbillings.infra.messaging.TransactionQueuePublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionQueuePublisher queuePublisher;

    @Test
    @DisplayName("Should create a transaction successfully")
    void shouldCreateTransaction() throws Exception {

        String accountRequestJson = """
            {
              "accountNumber": "acc-001",
              "balance": 100.00
            }
        """;

        mockMvc.perform(post("/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountRequestJson))
                .andExpect(status().isCreated());

        TransactionRequestDTO request = new TransactionRequestDTO(
                "acc-001",
                new BigDecimal("20.00"),
                PaymentType.D
        );

        doNothing().when(queuePublisher).publish(org.mockito.ArgumentMatchers.any());

        mockMvc.perform(post("/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(20.00))
                .andExpect(jsonPath("$.type").value("D"));
    }
}
