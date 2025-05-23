package com.bastos.ngbillings.applicaton.usecase;

import com.bastos.ngbillings.adapters.controller.dto.AccountRequestDTO;
import com.bastos.ngbillings.adapters.controller.dto.AccountResponseDTO;

/**
 * Interface responsável por recuperar uma conta existente
 * a partir do número da conta fornecido.
 * Retorna os dados da conta e suas transações associadas.
 *
 * @see Usecase
 * @see AccountResponseDTO
 *
 * @author bastos
 */
public interface FindAccountUsecase extends Usecase<String, AccountResponseDTO> {
}
