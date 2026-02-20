import com.desafio.votacao.dto.PautaRequestDTO;
import com.desafio.votacao.dto.PautaResponseDTO;
import com.desafio.votacao.dto.PautaResultadoDTO;
import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.PautaStatusEnum;
import com.desafio.votacao.model.Voto;
import com.desafio.votacao.model.VotoEnum;
import com.desafio.votacao.repository.PautaRepository;
import com.desafio.votacao.service.PautaService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Getter
@Setter
@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    @Test
    @DisplayName("Deve criar uma pauta com sucesso")
    void deveCriarPautaComSucesso() {
        PautaRequestDTO requestDTO = new PautaRequestDTO();
        requestDTO.setTitulo("Título da Pauta");

        Pauta pautaSalva = new Pauta();
        pautaSalva.setId(1L);
        pautaSalva.setTitulo("Título da Pauta");

        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaSalva);

        PautaResponseDTO resultado = pautaService.criar(requestDTO);

        assertNotNull(resultado);
        assertEquals("Título da Pauta", resultado.getTitulo());
        verify(pautaRepository, times(1)).save(any(Pauta.class));
    }

    @Test
    @DisplayName("Deve apurar votos e aprovar a pauta com sucesso")
    void deveApurarVotosEComAprovacao() {
        Long pautaId = 1L;

        Pauta pauta = new Pauta();
        pauta.setId(pautaId);
        pauta.setTitulo("Pauta de Teste");
        pauta.setStatus(PautaStatusEnum.ENCERRADA);

        Voto voto1 = new Voto(); voto1.setDecisao(VotoEnum.SIM);
        Voto voto2 = new Voto(); voto2.setDecisao(VotoEnum.SIM);
        Voto voto3 = new Voto(); voto3.setDecisao(VotoEnum.NAO);
        pauta.setVotos(List.of(voto1, voto2, voto3));

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        PautaResultadoDTO resultado = pautaService.apurarVotos(pautaId);

        assertNotNull(resultado);
        assertEquals(2, resultado.getVotosSim());
        assertEquals(1, resultado.getVotosNao());
        assertEquals("APROVADA", resultado.getStatus());

        verify(pautaRepository, times(1)).save(pauta);
    }

    @Test
    @DisplayName("Deve lançar exceção ao apurar pauta que não está encerrada")
    void deveLancarExcecaoAoApurarPautaNaoEncerrada() {
        // Arrange
        Long pautaId = 1L;
        Pauta pautaAberta = new Pauta();
        pautaAberta.setId(pautaId);
        pautaAberta.setStatus(PautaStatusEnum.ABERTA); // Status incorreto

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pautaAberta));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            pautaService.apurarVotos(pautaId);
        });

        assertTrue(exception.getMessage().contains("A pauta precisa estar ENCERRADA"));
        verify(pautaRepository, never()).save(any(Pauta.class));
    }
}