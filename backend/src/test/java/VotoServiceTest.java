import com.desafio.votacao.dto.VotoDTO;
import com.desafio.votacao.exception.BusinessException;
import com.desafio.votacao.model.Pauta;
import com.desafio.votacao.model.Voto;
import com.desafio.votacao.model.VotoEnum;
import com.desafio.votacao.repository.PautaRepository;
import com.desafio.votacao.repository.VotoRepository;
import com.desafio.votacao.service.VotoService;
import com.desafio.votacao.validator.VotoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Evita o erro de UnnecessaryStubbing
class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private VotoValidator votoValidator;

    @InjectMocks
    private VotoService votoService;

    @Test
    @DisplayName("Deve registrar um voto com sucesso")
    void deveRegistrarVotoComSucesso() {
        Long pautaId = 1L;
        VotoDTO dto = new VotoDTO("12345678901", VotoEnum.SIM);

        Pauta pauta = new Pauta();
        pauta.setId(pautaId);

        Voto votoSalvo = new Voto();
        votoSalvo.setId(10L);
        votoSalvo.setCpf(dto.getCpf());
        votoSalvo.setDecisao(dto.getDecisao());

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(votoRepository.save(any(Voto.class))).thenReturn(votoSalvo);

        VotoDTO resultado = votoService.registrarVoto(pautaId, dto);

        assertNotNull(resultado);
        assertEquals("12345678901", resultado.getCpf());
        verify(votoValidator).validateEncerramento(pautaId);
        verify(votoValidator).validateCpf(pautaId, dto.getCpf());
        verify(votoRepository).save(any(Voto.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar votar em pauta inexistente")
    void deveLancarExcecaoPautaInexistente() {
        // Arrange
        Long pautaId = 1L;
        VotoDTO dto = new VotoDTO("123", VotoEnum.SIM);
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BusinessException.class, () ->
                votoService.registrarVoto(pautaId, dto)
        );

        verify(votoRepository, never()).save(any());
    }
}