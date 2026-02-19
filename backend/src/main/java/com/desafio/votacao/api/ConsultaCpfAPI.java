package com.desafio.votacao.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("api-externa-cpf")
public class ConsultaCpfAPI {

    private final Random random = new Random();

    @GetMapping("{cpf}")
    public ResponseEntity<CpfResponseDTO> consultaCpf (@PathVariable String cpf) {

        if (!isAlgoritmoValido(cpf) || random.nextInt(10) >= 7) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new CpfResponseDTO("UNABLE_TO_VOTE"));
        }

        return ResponseEntity.ok(new CpfResponseDTO("ABLE_TO_VOTE"));
    }

    private boolean isAlgoritmoValido(String cpf) {
        if (cpf == null) return false;

        /** Remove máscara */
        String numeros = cpf.replaceAll("\\D", "");

        if (numeros.length() != 11 || numeros.matches("(\\d)\\1{10}")) return false;

        /** Cálcula os digitos verificadores */
        try {
            int d1 = calcularDigito(numeros.substring(0, 9), 10);
            int d2 = calcularDigito(numeros.substring(0, 9) + d1, 11);
            return numeros.equals(numeros.substring(0, 9) + d1 + d2);
        } catch (Exception e) {
            return false;
        }
    }

    private int calcularDigito(String str, int peso) {
        int soma = 0;

        for (int i = 0; i < str.length(); i++) {
            soma += (str.charAt(i) - '0') * peso--;
        }

        int resto = soma % 11;

        return (resto < 2) ? 0 : 11 - resto;
    }
}
