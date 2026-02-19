package com.desafio.votacao.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidade que gerencia o período de votação de uma pauta.
 * * NOTAS DE IMPLEMENTAÇÃO:
 * 1. @MapsId: Faz com que a Sessão compartilhe o mesmo ID da Pauta, garantindo
 * relacionamento 1:1  e eliminando chaves primárias desnecessárias.
 * 2. Encapsulamento: O método isAtiva() centraliza a regra de negócio do ciclo de vida da sessão.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sessao {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    private LocalDateTime dataAbertura = LocalDateTime.now();
    private LocalDateTime dataFechamento;

    /**
     * Verifica se a sessão ainda aceita votos com base no horário atual.
     * @return true se o momento atual for anterior ao fechamento.
     */

    public boolean isAtiva() {
        return dataFechamento != null && LocalDateTime.now().isBefore(dataFechamento);
    }
}
