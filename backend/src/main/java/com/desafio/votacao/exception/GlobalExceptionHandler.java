package com.desafio.votacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public Issue handleBusinessException(BusinessException e) {
        return new Issue(e.getMessage(), HttpStatus.BAD_REQUEST.name());
    }

    @ExceptionHandler(VotoStatusException.class)
    public ResponseEntity<Issue> handleVotoStatusException(VotoStatusException ex) {
        if (ex.getReason() != null && ex.getReason().contains("UNABLE_TO_VOTE")) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Issue("UNABLE_TO_VOTE", HttpStatus.NOT_FOUND.name()));
        }

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new Issue(ex.getReason(), ex.getStatusCode().toString()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Issue> handleValidationErrors(MethodArgumentNotValidException ex) {
        String mensagemErro = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        Issue issue = new Issue(mensagemErro, HttpStatus.NOT_FOUND.name());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(issue);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Issue> handleJsonError(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Issue("JSON_FORA_DO_PADRAO", HttpStatus.BAD_REQUEST.name()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Issue handleException(Exception e){
        return new Issue("ERRO_INTERNO_SERVIDOR", HttpStatus.INTERNAL_SERVER_ERROR.name());
    }
}