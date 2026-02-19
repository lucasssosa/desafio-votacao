package com.desafio.votacao.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class VotoStatusException extends ResponseStatusException {

    public VotoStatusException(HttpStatusCode statusCode, String message) {
        super(statusCode, message);
    }
}
