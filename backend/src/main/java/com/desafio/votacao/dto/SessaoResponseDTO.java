package com.desafio.votacao.dto;

import com.desafio.votacao.model.Sessao;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessaoResponseDTO {

    private Long id;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private Long pautaId;

    public static SessaoResponseDTO fromEntity(Sessao sessao) {
        SessaoResponseDTO dto = new SessaoResponseDTO();

        dto.setId(sessao.getId());
        dto.setDataAbertura(sessao.getDataAbertura());
        dto.setDataFechamento(sessao.getDataFechamento());
        dto.setPautaId(sessao.getPauta().getId());

        return dto;
    }
}
