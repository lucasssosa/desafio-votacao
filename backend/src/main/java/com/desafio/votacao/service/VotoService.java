package com.desafio.votacao.service;

import com.desafio.votacao.dto.VotoDTO;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.Voto;
import com.desafio.votacao.repository.PautaRepository;
import com.desafio.votacao.repository.VotoRepository;
import com.desafio.votacao.validator.VotoValidator;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Substitui o @Autowired, garantindo imutabilidade
public class VotoService {

    private final VotoRepository votoRepository;
    private final PautaRepository pautaRepository;
    private final VotoValidator votoValidator;

    /**
     * Registra o voto de um associado em uma pauta.
     * * NOTAS DE IMPLEMENTAÇÃO:
     * 1. O VotoValidator garante que as regras (tempo e CPF) sejam cumpridas
     * antes de qualquer operação de banco.
     * 2. @Transactional: Se o votoRepository.save falhar por qualquer motivo técnico,
     * nada é persistido, mantendo a integridade dos dados.
     */
    @Transactional
    public VotoDTO registrarVoto(Long pautaId, VotoDTO votoDTO) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));

        votoValidator.validateEncerramento(pautaId);
        votoValidator.validateCpf(pautaId, votoDTO.getCpf());

        Voto voto = new Voto();
        voto.setPauta(pauta);
        voto.setCpf(votoDTO.getCpf());
        voto.setDecisao(votoDTO.getDecisao());

        Voto votoSalvo = votoRepository.save(voto);

        return VotoDTO.fromEntity(votoSalvo);
    }

    public List<VotoDTO> buscarPorPauta(Long pautaId) {

        return votoRepository.findByPautaId(pautaId).stream()
                .map(VotoDTO::fromEntity)
                .toList();
    }
}
