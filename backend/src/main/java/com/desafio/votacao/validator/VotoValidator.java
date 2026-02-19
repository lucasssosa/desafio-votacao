package com.desafio.votacao.validator;

import com.desafio.votacao.client.CpfClient;
import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.model.Sessao;
import com.desafio.votacao.repository.SessaoRepository;
import com.desafio.votacao.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VotoValidator {

    private final VotoRepository votoRepository;
    private final SessaoRepository sessaoRepository;
    private final CpfClient cpfClient;

    public void validateCpf(Long pautaId, String cpf) {
        if (votoRepository.existsByPautaIdAndCpf(pautaId, cpf)) {
            throw new BusinessException("Este CPF já votou nesta pauta!");
        }

        cpfClient.validar(cpf);
    }

    public void validateEncerramento(Long id) {

        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pauta não encontrada"));

        if (sessao.getDataFechamento() == null || LocalDateTime.now().isAfter(sessao.getDataFechamento())) {
            throw new BusinessException("A votação para esta pauta já está encerrada!");
        }
    }
}
