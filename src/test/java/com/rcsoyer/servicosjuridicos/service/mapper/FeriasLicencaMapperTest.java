package com.rcsoyer.servicosjuridicos.service.mapper;

import static com.rcsoyer.servicosjuridicos.domain.feriaslicenca.FeriasLicencaTipo.LICENCA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.feriaslicenca.FeriasLicenca;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {FeriasLicencaMapperImpl.class, AdvogadoMapperImpl.class})
class FeriasLicencaMapperTest {

    @Autowired
    private FeriasLicencaMapper mapper;

    @Test
    void toDto() {
        final var feriasLicenca = new FeriasLicenca()
                                      .setId(1L)
                                      .setAdvogado(new Advogado().setId(1L))
                                      .setDtInicio(LocalDate.now())
                                      .setDtFim(LocalDate.now())
                                      .setTipo(LICENCA);

        final var dtoResult = mapper.toDto(feriasLicenca);

        assertEquals(feriasLicenca.getId(), dtoResult.getId());
        assertEquals(feriasLicenca.getDtInicio(), dtoResult.getDtInicio());
        assertEquals(feriasLicenca.getDtFim(), dtoResult.getDtFim());
        assertSame(feriasLicenca.getTipo(), dtoResult.getTipo());
        assertEquals(feriasLicenca.getAdvogado().getId(), dtoResult.getAdvogadoId());
    }

    @Test
    void toEntity() {
        final var feriasLicencaDto = new FeriasLicencaDTO()
                                         .setId(1L)
                                         .setAdvogadoId(1L)
                                         .setDtInicio(LocalDate.now())
                                         .setDtFim(LocalDate.now())
                                         .setTipo(LICENCA);

        final var feriasLicencaResult = mapper.toEntity(feriasLicencaDto);

        assertEquals(feriasLicencaDto.getId(), feriasLicencaResult.getId());
        assertEquals(feriasLicencaDto.getDtInicio(), feriasLicencaResult.getDtInicio());
        assertEquals(feriasLicencaDto.getDtFim(), feriasLicencaResult.getDtFim());
        assertSame(feriasLicencaDto.getTipo(), feriasLicencaResult.getTipo());
        assertEquals(feriasLicencaDto.getAdvogadoId(), feriasLicencaResult.getAdvogado().getId());
    }
}
