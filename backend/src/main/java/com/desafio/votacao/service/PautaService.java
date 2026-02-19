package com.desafio.votacao.service;

import com.desafio.votacao.dto.PautaRequestDTO;
import com.desafio.votacao.dto.PautaResponseDTO;
import com.desafio.votacao.dto.PautaResultadoDTO;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.Voto;
import com.desafio.votacao.model.VotoEnum;
import com.desafio.votacao.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;

    /**
     * * NOTAS DE IMPLEMENTAÇÃO:
     * 1. @Transactional(readOnly = true): arantir otimização e clareza.
     */
    @Transactional(readOnly = true)
    public List<PautaResponseDTO> listarTodas() {
        return pautaRepository.findAll().stream()
                .map(PautaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public PautaResponseDTO criar(PautaRequestDTO dto) {
        Pauta pauta = dto.toEntity();
        Pauta pautaSalva = pautaRepository.save(pauta);
        return PautaResponseDTO.fromEntity(pautaSalva);
    }

    @Transactional(readOnly = true)
    public PautaResultadoDTO apurarVotos(Long pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));

        var contagem = pauta.getVotos().stream()
                .collect(Collectors.groupingBy(Voto::getDecisao, Collectors.counting()));

        long votosPositivos = contagem.getOrDefault(VotoEnum.SIM, 0L);
        long votosNegativos = contagem.getOrDefault(VotoEnum.NAO, 0L);

        String status = definirStatus(votosPositivos, votosNegativos);

        return new PautaResultadoDTO(pauta.getTitulo(), votosPositivos, votosNegativos, status);
    }

    private String definirStatus(long sim, long nao) {
        if (sim > nao) return "APROVADA";
        if (nao > sim) return "REPROVADA";
        return "EMPATE";
    }
}
