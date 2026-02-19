package com.desafio.votacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade que registra o voto individual de um associado.
 * * NOTAS DE IMPLEMENTAÇÃO:
 * 1. @ManyToOne(LAZY): Evita o carregamento desnecessário da Pauta ao listar votos.
 * 2. @Enumerated: Salva o texto (SIM/NAO), diferente de um Booleano,
 * o Enum permite extensibilidade. Caso o sistema precise evoluir para aceitar
 * "VOTO_NULO" ou "ABSTENCAO", basta adicionar o novo valor ao Enum,
 * sem necessidade de alterar a estrutura da tabela no banco de dados ou a lógica.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpf;

    @Enumerated(EnumType.STRING)
    private VotoEnum decisao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pauta_id")
    @JsonIgnore
    private Pauta pauta;
}
