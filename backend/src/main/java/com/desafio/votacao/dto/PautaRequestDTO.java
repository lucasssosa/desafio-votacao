package com.desafio.votacao.dto;

import com.desafio.votacao.model.Pauta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautaRequestDTO {

    @NotBlank(message = "O título da pauta é obrigatório.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    private String descricao;

    public Pauta toEntity() {
        Pauta pauta = new Pauta();
        pauta.setTitulo(this.titulo);
        pauta.setDescricao(this.descricao);
        return pauta;
    }
}