package com.bastos.ngbillings.applicaton.service;

import com.bastos.ngbillings.adapters.psersistence.entity.AccountEntity;
import com.bastos.ngbillings.adapters.psersistence.entity.TransactionEntity;
import com.bastos.ngbillings.adapters.psersistence.repository.AccountRepository;
import com.bastos.ngbillings.adapters.psersistence.repository.TransactionRepository;
import com.bastos.ngbillings.applicaton.exception.InsufficientFundsBadRequestException;
import com.bastos.ngbillings.applicaton.usecase.CreateTransactionUsecase;
import com.bastos.ngbillings.adapters.controller.dto.TransactionRequestDTO;
import com.bastos.ngbillings.adapters.controller.dto.TransactionResponseDTO;
import com.bastos.ngbillings.applicaton.exception.PaymentDeclinedBadRequestException;
import com.bastos.ngbillings.domain.model.PaymentType;
import com.bastos.ngbillings.domain.vo.TransactionVO;
import com.bastos.ngbillings.applicaton.exception.AccountNotFoundException;
import com.bastos.ngbillings.infra.PaymentGateway;
import com.bastos.ngbillings.infra.messaging.TransactionEvent;
import com.bastos.ngbillings.infra.messaging.TransactionQueuePublisher;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CreateTransactionService implements CreateTransactionUsecase {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CreateTransactionService.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository repository;
    private final PaymentGateway paymentGateway;
    private final TransactionQueuePublisher queuePublisher;

    @Autowired
    public CreateTransactionService(AccountRepository accountRepository,
                                    TransactionRepository transactionRepository,
                                    PaymentGateway paymentGateway,
                                    TransactionQueuePublisher queuePublisher) {

        this.accountRepository = accountRepository;
        this.repository = transactionRepository;
        this.paymentGateway = paymentGateway;
        this.queuePublisher = queuePublisher;
    }

    @Transactional
    @Override
    public TransactionResponseDTO execute(TransactionRequestDTO request) {
        AccountEntity account = accountRepository.findByAccountNumber(request.accountNumber())
                .orElseThrow(AccountNotFoundException::new);

        TransactionVO vo = new TransactionVO(request.amount(), request.type());
        BigDecimal totalWithFee = vo.withFee();

        if (account.getBalance().compareTo(totalWithFee) < 0) {
            throw new InsufficientFundsBadRequestException();
        }

        if (request.type() == PaymentType.C) {
            if (!paymentGateway.authorize("4111-xxxx-xxxx-1111")) {
                throw new PaymentDeclinedBadRequestException();
            }
        }

        account.setBalance(account.getBalance().subtract(totalWithFee));

        TransactionEntity transaction = new TransactionEntity(
                account,
                request.type(),
                request.amount()
        );

        repository.save(transaction);
        log.info("Transaction created: {}", transaction);

        accountRepository.save(account);
        log.info("Account {} updated with new balance: {}", account.getAccountNumber(), account.getBalance());

        queuePublisher.publish(new TransactionEvent(
                account.getAccountNumber(),
                request.amount(),
                request.type()).toString()
        );

        return new TransactionResponseDTO(transaction.getAmount(), transaction.getPaymentType());
    }
}
