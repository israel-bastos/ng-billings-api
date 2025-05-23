package com.bastos.ngbillings.applicaton.service;

import com.bastos.ngbillings.adapters.controller.dto.AccountRequestDTO;
import com.bastos.ngbillings.adapters.controller.dto.AccountResponseDTO;
import com.bastos.ngbillings.adapters.psersistence.entity.AccountEntity;
import com.bastos.ngbillings.adapters.psersistence.repository.AccountRepository;
import com.bastos.ngbillings.applicaton.usecase.CreateAccountUsecase;
import com.bastos.ngbillings.applicaton.exception.ExistenceAccountBadRequestException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService implements CreateAccountUsecase {

    private static final Logger log =  org.slf4j.LoggerFactory.getLogger(CreateAccountService.class);

    private final AccountRepository repository;

    public CreateAccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountResponseDTO execute(AccountRequestDTO request) {
        if (repository.existsByAccountNumber(request.accountNumber())) {
            throw new ExistenceAccountBadRequestException();
        }

        AccountEntity entity = new AccountEntity(
                request.accountNumber(),
                request.balance()
        );

        repository.save(entity);
        log.info("Account {} created", request.accountNumber());

        return new AccountResponseDTO(
                entity.getAccountNumber(),
                entity.getBalance(),
                null
        );
    }
}
