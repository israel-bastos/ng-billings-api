package com.bastos.ngbillings.applicaton.usecase;

import com.bastos.ngbillings.adapters.controller.dto.TransactionRequestDTO;
import com.bastos.ngbillings.adapters.controller.dto.TransactionResponseDTO;

/**
 * Interface que representa o caso de uso de criação de transações.
 * Realiza operações financeiras como débito, crédito ou pix em uma conta existente,
 * aplicando regras de validação e taxas conforme o tipo de pagamento.
 *
 * @see Usecase
 * @see TransactionRequestDTO
 * @see TransactionResponseDTO
 *
 * @author bastos
 */
public interface CreateTransactionUsecase extends Usecase<TransactionRequestDTO, TransactionResponseDTO> {
}
