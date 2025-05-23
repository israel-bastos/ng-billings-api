package com.bastos.ngbillings.adapters.controller;

import com.bastos.ngbillings.applicaton.usecase.CreateAccountUsecase;
import com.bastos.ngbillings.adapters.controller.dto.AccountRequestDTO;
import com.bastos.ngbillings.adapters.controller.dto.AccountResponseDTO;
import com.bastos.ngbillings.applicaton.usecase.FindAccountUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final CreateAccountUsecase createAccountUsecase;
    private final FindAccountUsecase findAccountUsecase;

    @Autowired
    public AccountController(CreateAccountUsecase createAccountUsecase, FindAccountUsecase findAccountUsecase) {
        this.createAccountUsecase = createAccountUsecase;
        this.findAccountUsecase = findAccountUsecase;
    }

    @Operation(summary = "Criar nova conta", description = "Cria uma nova conta com número e saldo inicial")
    @ApiResponse(responseCode = "201", description = "Conta criada com sucesso")
    @ApiResponse(responseCode = "404", description = "Conta já existente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO create(@RequestBody @Valid AccountRequestDTO request) {
        return createAccountUsecase.execute(request);
    }

    @Operation(summary = "Buscar conta", description = "Busca conta por número e retorna saldo com transações vinculadas")
    @ApiResponse(responseCode = "200", description = "Conta encontrada")
    @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    @ApiResponse(responseCode = "400", description = "Conta com saldo insuficiente")
    @GetMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseDTO get(@PathVariable String accountNumber) {
        return findAccountUsecase.execute(accountNumber);
    }
}
