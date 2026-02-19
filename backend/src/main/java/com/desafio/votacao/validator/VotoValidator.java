package com.desafio.votacao.validator;

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

    public void validateCpf(Long pautaId, String cpf) {
        if (votoRepository.existsByPautaIdAndCpf(pautaId, cpf)) {
            throw new RuntimeException("Este CPF já votou nesta pauta!");
        }
    }

    public void validateEncerramento(Long id) {

        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));

        if (sessao.getDataFechamento() == null || LocalDateTime.now().isAfter(sessao.getDataFechamento())) {
            throw new RuntimeException("A votação para esta pauta já está encerrada!");
        }
    }
}
