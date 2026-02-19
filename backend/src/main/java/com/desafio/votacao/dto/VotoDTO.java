package com.desafio.votacao.dto;

import com.desafio.votacao.model.Voto;
import com.desafio.votacao.model.VotoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoDTO {

    @NotBlank(message = "Obrigatório informar CPF.")
    private String cpf;

    @NotNull(message = "A decisão (SIM/NAO) deve ser informada.")
    private VotoEnum decisao;

    public static VotoDTO fromEntity(Voto voto) {

        VotoDTO dto = new VotoDTO();
        dto.setCpf(voto.getCpf());
        dto.setDecisao(voto.getDecisao());

        return dto;
    }
}
