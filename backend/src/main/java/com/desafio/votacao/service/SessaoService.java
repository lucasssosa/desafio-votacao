package com.desafio.votacao.service;

import com.desafio.votacao.dto.SessaoResponseDTO;
import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.Sessao;
import com.desafio.votacao.repository.PautaRepository;
import com.desafio.votacao.repository.SessaoRepository;
import com.desafio.votacao.validator.SessaoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    private final SessaoValidator sessaoValidator;

    @Transactional
    public SessaoResponseDTO abrir(Long pautaId, Integer segundos) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new BusinessException("Pauta não encontrada"));

        sessaoValidator.validadeSessao(pauta);

        int duracao = (segundos == null || segundos <= 0) ? 60 : segundos;

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setDataAbertura(LocalDateTime.now());
        sessao.setDataFechamento(LocalDateTime.now().plusSeconds(duracao));

        return SessaoResponseDTO.fromEntity(sessaoRepository.save(sessao));
    }
}
