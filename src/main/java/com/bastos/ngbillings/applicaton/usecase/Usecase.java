package com.bastos.ngbillings.applicaton.usecase;

/**
 * Interface genérica para casos de uso que recebem uma entrada e retornam uma saída.
 *
 * @param <I> tipo do objeto de entrada
 * @param <O> tipo do objeto de saída
 *
 * @author bastos
 */
public interface Usecase<I, O> {
    O execute(I input);
}
