package com.desafio.votacao.validator;

import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.model.Pauta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessaoValidator {

    public void validadeSessao(Pauta pauta) {
        if (pauta.getSessao() != null) {
            throw new BusinessException("Esta pauta já possui uma sessão aberta ou finalizada.");
        }
    }
}
