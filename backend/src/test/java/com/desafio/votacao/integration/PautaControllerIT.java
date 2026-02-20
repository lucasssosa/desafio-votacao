package com.desafio.votacao.integration;

import com.desafio.votacao.dto.*;
import com.desafio.votacao.model.VotoEnum;
import com.desafio.votacao.validator.VotoValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PautaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VotoValidator votoValidator;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    @DisplayName("Deve executar o fluxo completo de vida de uma pauta")
    void deveExecutarFluxoCompleto() throws Exception {

        doNothing().when(votoValidator).validateEncerramento(anyLong());
        doNothing().when(votoValidator).validateCpf(anyLong(), anyString());

        PautaRequestDTO pautaReq = new PautaRequestDTO();
        pautaReq.setTitulo("Pauta de Teste");
        MvcResult pautaResult = mockMvc.perform(post("/api/v1/pautas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pautaReq)))
                .andExpect(status().isCreated())
                .andReturn();

        Long pautaId = objectMapper.readTree(pautaResult.getResponse().getContentAsString()).get("id").asLong();

        SessaoAberturaDTO sessaoReq = new SessaoAberturaDTO();
        sessaoReq.setSegundos(1);
        mockMvc.perform(post("/api/v1/pautas/" + pautaId + "/sessao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessaoReq)))
                .andExpect(status().isCreated());

        VotoDTO votoReq = new VotoDTO("12345678901", VotoEnum.SIM);
        mockMvc.perform(post("/api/v1/pautas/" + pautaId + "/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(votoReq)))
                .andExpect(status().isCreated());

        Thread.sleep(2000);

        mockMvc.perform(put("/api/v1/pautas/" + pautaId + "/resultado"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votosSim").value(1));
    }

    @Test
    @DisplayName("Deve buscar todas as pautas")
    void deveBuscarTodasPautas() throws Exception {
        mockMvc.perform(get("/api/v1/pautas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}