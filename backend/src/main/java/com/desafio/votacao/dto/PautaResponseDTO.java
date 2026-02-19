package com.desafio.votacao.dto;

import com.desafio.votacao.model.Pauta;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautaResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private boolean aberta;

    public static PautaResponseDTO fromEntity(Pauta pauta) {
        PautaResponseDTO dto = new PautaResponseDTO();
        dto.setId(pauta.getId());
        dto.setTitulo(pauta.getTitulo());
        dto.setDescricao(pauta.getDescricao());

        dto.setAberta(pauta.getSessao() != null && pauta.getSessao().isAtiva());

        return dto;
    }
}
