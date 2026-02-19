package com.desafio.votacao.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautaResultadoDTO {

    private String titulo;
    private Long votosSim;
    private Long votosNao;
    private String status;
}
