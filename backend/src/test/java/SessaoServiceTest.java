import com.desafio.votacao.dto.SessaoResponseDTO;
import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.Sessao;
import com.desafio.votacao.repository.PautaRepository;
import com.desafio.votacao.repository.SessaoRepository;
import com.desafio.votacao.service.SessaoService;
import com.desafio.votacao.validator.SessaoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;
    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private SessaoValidator sessaoValidator;

    @InjectMocks
    private SessaoService sessaoService;

    @Test
    @DisplayName("Deve abrir sessão com 60s quando segundos for nulo ou zero")
    void deveAbrirSessaoComTempoPadrao() {
        Long pautaId = 1L;
        Pauta pauta = new Pauta();
        pauta.setId(pautaId);

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(sessaoRepository.save(any(Sessao.class))).thenAnswer(i -> i.getArguments()[0]);

        SessaoResponseDTO result = sessaoService.abrir(pautaId, null);

        assertNotNull(result);
        verify(sessaoValidator).validadeSessao(pauta);
        verify(sessaoRepository).save(argThat(s ->
                s.getDataFechamento().isBefore(LocalDateTime.now().plusSeconds(62))));
    }

    @Test
    @DisplayName("Deve abrir sessão com tempo personalizado corretamente")
    void deveAbrirSessaoComTempoPersonalizado() {
        // Arrange
        Long pautaId = 1L;
        Integer segundos = 300; // 5 minutos
        Pauta pauta = new Pauta();
        pauta.setId(pautaId);

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(sessaoRepository.save(any(Sessao.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        sessaoService.abrir(pautaId, segundos);

        // Assert
        verify(sessaoRepository).save(argThat(s ->
                s.getDataFechamento().isAfter(LocalDateTime.now().plusSeconds(290))));
    }

    @Test
    @DisplayName("Deve lançar exceção quando pauta não for encontrada")
    void deveLancarExcecaoPautaInexistente() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> sessaoService.abrir(1L, 60));
    }
}