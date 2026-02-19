package com.desafio.votacao.client;

import com.desafio.votacao.api.CpfResponseDTO;
import com.desafio.votacao.exception.VotoStatusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class CpfClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.cpf.base-url}")
    private String baseUrl;

    public void validar(String cpf) {
        String url = baseUrl + "/" + cpf;

        try {
            restTemplate.getForEntity(url, CpfResponseDTO.class);
        } catch (RestClientResponseException e) {
            throw new VotoStatusException(HttpStatus.NOT_FOUND, "UNABLE_TO_VOTE");
        } catch (Exception e) {
            throw new VotoStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Erro ao consultar serviço de CPF");
        }
    }
}
