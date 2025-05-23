package com.bastos.ngbillings.applicaton.service;

import com.bastos.ngbillings.adapters.controller.dto.AccountResponseDTO;
import com.bastos.ngbillings.adapters.controller.dto.TransactionResponseDTO;
import com.bastos.ngbillings.adapters.psersistence.entity.AccountEntity;
import com.bastos.ngbillings.adapters.psersistence.repository.AccountRepository;
import com.bastos.ngbillings.applicaton.exception.AccountNotFoundException;
import com.bastos.ngbillings.applicaton.usecase.FindAccountUsecase;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FindAccountService implements FindAccountUsecase {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FindAccountService.class);

    private final AccountRepository accountRepository;

    public FindAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountResponseDTO execute(String accountNumber) {
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(AccountNotFoundException::new);

        Set<TransactionResponseDTO> transactions = account.getTransactions().stream()
                .map(t -> new TransactionResponseDTO(t.getAmount(), t.getPaymentType()))
                .collect(Collectors.toSet());

        log.info("Account {} found", accountNumber);

        return new AccountResponseDTO(account.getAccountNumber(), account.getBalance(), transactions);
    }
}
