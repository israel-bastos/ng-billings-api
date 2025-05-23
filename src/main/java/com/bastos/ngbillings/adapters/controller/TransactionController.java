package com.bastos.ngbillings.adapters.controller;

import com.bastos.ngbillings.applicaton.usecase.CreateTransactionUsecase;
import com.bastos.ngbillings.adapters.controller.dto.TransactionRequestDTO;
import com.bastos.ngbillings.adapters.controller.dto.TransactionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    private final CreateTransactionUsecase createTransactionUsecase;

    @Autowired
    public TransactionController(CreateTransactionUsecase createTransactionUsecase) {
        this.createTransactionUsecase = createTransactionUsecase;
    }

    @Operation(summary = "Criar transação", description = "Aplica taxa e lança transação sobre uma conta existente")
    @ApiResponse(responseCode = "200", description = "Transação efetuada com sucesso")
    @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    @ApiResponse(responseCode = "400", description = "Saldo insuficiente")
    @ApiResponse(responseCode = "400", description = "Pagamento recusado")
    @PostMapping
    public TransactionResponseDTO transact(@RequestBody @Valid TransactionRequestDTO request) {
        return createTransactionUsecase.execute(request);
    }
}
