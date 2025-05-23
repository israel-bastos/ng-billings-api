package com.bastos.ngbillings.applicaton.usecase;

import com.bastos.ngbillings.adapters.controller.dto.AccountRequestDTO;
import com.bastos.ngbillings.adapters.controller.dto.AccountResponseDTO;

/**
 * Interface que representa o caso de uso de criação de conta.
 * Recebe os dados necessários para a criação de uma nova conta
 * e retorna as informações da conta criada.
 *
 * @see Usecase
 * @see AccountRequestDTO
 * @see AccountResponseDTO
 *
 * @author bastos
 */
public interface CreateAccountUsecase extends Usecase<AccountRequestDTO, AccountResponseDTO> {
}
