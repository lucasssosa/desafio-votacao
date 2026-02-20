package com.desafio.votacao.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.PautaStatusEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautaResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private PautaStatusEnum status;
    private SessaoResponseDTO sessao;
    private List<VotoDTO> votos;

    public static PautaResponseDTO fromEntity(Pauta pauta) {
        PautaResponseDTO dto = new PautaResponseDTO();
        dto.setId(pauta.getId());
        dto.setTitulo(pauta.getTitulo());
        dto.setDescricao(pauta.getDescricao());
        dto.setStatus(pauta.getStatusAtual());

        if (pauta.getSessao() != null) {
            dto.setSessao(SessaoResponseDTO.fromEntity(pauta.getSessao()));
        }

        if (pauta.getVotos() != null) {
            dto.setVotos(pauta.getVotos().stream()
                .map(VotoDTO::fromEntity)
                .collect(Collectors.toList()));
        }

        return dto;
    }
}
