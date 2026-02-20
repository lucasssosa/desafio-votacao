package com.desafio.votacao.controller;

import com.desafio.votacao.dto.*;
import com.desafio.votacao.service.PautaService;
import com.desafio.votacao.service.SessaoService;
import com.desafio.votacao.service.VotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pela gestão do ciclo de vida de uma Pauta,
 * incluindo abertura de sessões, votação e apuração de resultados.
 ** Versionamento:
 * Adotado o versionamento via URL (/v1) para garantir a compatibilidade
 * entre contratos. Essa abordagem simplifica o roteamento em camadas de
 * infraestrutura e evita quebras para os clientes atuais.
 */

@RestController

@RequestMapping("/api/v1/pautas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PautaController {

    private final PautaService pautaService;
    private final SessaoService sessaoService;
    private final VotoService votoService;

    /** Cria uma nova pauta para votação. */
    @PostMapping
    public ResponseEntity<PautaResponseDTO> criar(@RequestBody PautaRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(pautaService.criar(dto));
    }

    /** Abre uma sessão de votação para uma pauta. */
    @PostMapping("/{pautaId}/sessao")
    public ResponseEntity<SessaoResponseDTO> abrir(@PathVariable Long pautaId, @RequestBody SessaoAberturaDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoService.abrir(pautaId, dto.getSegundos()));
    }

    /** Registra o voto de um associado. */
    @PostMapping("/{pautaId}/votos")
    public ResponseEntity<VotoDTO> votar(@PathVariable Long pautaId,@Valid @RequestBody VotoDTO votoDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(votoService.registrarVoto(pautaId, votoDTO));
    }

    /** Apura e retorna o resultado consolidado da votação. */
    @PutMapping("/{pautaId}/resultado")
    public ResponseEntity<PautaResultadoDTO> verResultado(@PathVariable Long pautaId) {

        return ResponseEntity.ok(pautaService.apurarVotos(pautaId));
    }

    /** Listagem de pautas cadastradas. */
    @GetMapping
    public ResponseEntity<List<PautaResponseDTO>> buscarTodas() {

        return ResponseEntity.ok(pautaService.listarTodas());
    }

    /** Lista todos os votos registrados para uma pauta. */
    @GetMapping("/{pautaId}/votos")
    public ResponseEntity<List<VotoDTO>> buscarVotos(@PathVariable Long pautaId) {

        return ResponseEntity.ok(votoService.buscarPorPauta(pautaId));
    }
}