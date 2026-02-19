package com.desafio.votacao.repository;

import com.desafio.votacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByPautaIdAndCpf(Long pautaId, String cpf);

    List<Voto> findByPautaId(Long pautaId);
}