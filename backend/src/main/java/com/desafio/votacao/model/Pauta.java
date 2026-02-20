package com.desafio.votacao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidade que representa uma Pauta de votação no sistema.
 * * NOTAS DE IMPLEMENTAÇÃO:
 * 1. @Getter e @Setter: Substituem o @Data para evitar loops infinitos em logs e
 * garantir melhor performance no Hibernate.
 * 2. @NoArgsConstructor: Gera o construtor padrão sem argumentos, exigência do JPA
 * para a manipulação e persistência da entidade.
 * 3. @AllArgsConstructor: Gera um construtor com todos os atributos, facilitando
 * a criação de instâncias em testes e métodos auxiliares.
 * 4. cascade = CascadeType.ALL: Qualquer operação na Pauta (salvar, excluir, atualizar)
 * será replicada automaticamente na Sessão, garantindo que a Sessão seja gerenciada
 * como parte do ciclo de vida da Pauta.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private PautaStatusEnum status; 

    @OneToOne(mappedBy = "pauta", cascade = CascadeType.ALL)
    private Sessao sessao;

    @OneToMany(mappedBy = "pauta", fetch = FetchType.LAZY)
    private List<Voto> votos;

    public PautaStatusEnum getStatusAtual() {
        // Se foi apurado (status persistido), ele manda.
        if (this.status != null) {
            return this.status;
        }
        
        // Caso contrário, calcula o estado volátil baseado na sessão.
        if (sessao == null) return PautaStatusEnum.CRIADA;
        
        return sessao.isAtiva() ? PautaStatusEnum.ABERTA : PautaStatusEnum.ENCERRADA;
    }
}