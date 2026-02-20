package com.desafio.votacao.validator;

import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.PautaStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PautaValidator {

    public void validadeStatus(Pauta pauta) {
        if (pauta.getStatusAtual() != PautaStatusEnum.ENCERRADA) {
            throw new BusinessException("A pauta precisa estar ENCERRADA para ser apurada. Status atual: " + pauta.getStatusAtual());
        }
    }
}
