package com.rcsoyer.servicosjuridicos.service.mapper;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoCreateUpdateDto;
import com.rcsoyer.servicosjuridicos.service.dto.QueryParamsCoordenacao;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CoordenacaoJuridicaMapperImpl.class, AssuntoMapperImpl.class})
class CoordenacaoJuridicaMapperTest {
    
    @Inject
    private CoordenacaoJuridicaMapper mapper;
    
    @Test
    void toEntity() {
        CoordenacaoCreateUpdateDto coordenacaoJuridicaDto = createCoordenacaoJuridicaDto();
        final var coordenacaoJuridica = mapper.toEntity(coordenacaoJuridicaDto);
        
        assertEquals(coordenacaoJuridicaDto.getId(), coordenacaoJuridica.getId());
        assertEquals(coordenacaoJuridicaDto.getCentena(), coordenacaoJuridica.getCentena());
        assertEquals(coordenacaoJuridicaDto.getNome(), coordenacaoJuridica.getNome());
        assertEquals(coordenacaoJuridicaDto.getSigla(), coordenacaoJuridica.getSigla());
        
        final List<AssuntoDTO> assuntosDto = coordenacaoJuridicaDto.getAssuntos()
                                                                   .stream()
                                                                   .sorted(comparing(AssuntoDTO::getId))
                                                                   .collect(toList());
        
        final List<Assunto> assuntos = coordenacaoJuridica.getAssuntos()
                                                          .stream()
                                                          .sorted(comparing(Assunto::getId))
                                                          .collect(toList());
        
        IntStream.range(0, 2)
                 .forEach(index -> {
                     AssuntoDTO dto = assuntosDto.get(index);
                     Assunto assunto = assuntos.get(index);
            
                     assertEquals(dto.getId(), assunto.getId());
                     assertEquals(dto.isAtivo(), assunto.getAtivo());
                     assertEquals(dto.getDescricao(), assunto.getDescricao());
                     assertEquals(dto.getPeso(), assunto.getPeso());
                 });
    }
    
    @Test
    void toEntityWithAssuntosIds() {
        final var assuntosIds = Set.of(1L, 2L);
        QueryParamsCoordenacao coordenacaoJuridicaDto = new QueryParamsCoordenacao()
                                                            .setId(1L)
                                                            .setCentena("423")
                                                            .setNome("Lack of coodernation")
                                                            .setSigla("OCU")
                                                            .setAssuntos(assuntosIds);
        final var coordenacaoJuridica = mapper.toEntity(coordenacaoJuridicaDto);
        
        assertEquals(coordenacaoJuridicaDto.getId(), coordenacaoJuridica.getId());
        assertEquals(coordenacaoJuridicaDto.getCentena(), coordenacaoJuridica.getCentena());
        assertEquals(coordenacaoJuridicaDto.getNome(), coordenacaoJuridica.getNome());
        assertEquals(coordenacaoJuridicaDto.getSigla(), coordenacaoJuridica.getSigla());
        
        final Set<Long> assuntos = coordenacaoJuridica.getAssuntos()
                                                      .stream()
                                                      .map(Assunto::getId)
                                                      .collect(toSet());
        
        assertEquals(assuntosIds, assuntos);
    }
    
    @Test
    void fromId() {
        final var dto = new CoordenacaoCreateUpdateDto().setId(1L);
        CoordenacaoJuridica coordenacaoJuridica = mapper.fromId(dto.getId());
        
        assertEquals(dto.getId(), coordenacaoJuridica.getId());
    }
    
    @Test
    void toDto() {
        final var assunto1 = new Assunto()
                                 .setId(1L)
                                 .setDescricao("Futebol")
                                 .setAtivo(Boolean.TRUE)
                                 .setPeso(1)
                                 .setDescricao("frequentemente usado em bares");
        final var assunto2 = new Assunto()
                                 .setId(2L)
                                 .setDescricao("Cinema")
                                 .setAtivo(Boolean.FALSE)
                                 .setPeso(2)
                                 .setDescricao("frequentemente usado na falta de maior intimidade");
        final var coordenacao = new CoordenacaoJuridica()
                                    .setId(1L)
                                    .setCentena("423")
                                    .setNome("Lack of coodernation")
                                    .setSigla("OCU")
                                    .addAssuntos(Set.of(assunto1, assunto2));
        
        final var coordenacaoJuridicaDto = mapper.toDto(coordenacao);
        
        assertEquals(coordenacao.getId(), coordenacaoJuridicaDto.getId());
        assertEquals(coordenacao.getCentena(), coordenacaoJuridicaDto.getCentena());
        assertEquals(coordenacao.getNome(), coordenacaoJuridicaDto.getNome());
        assertEquals(coordenacao.getSigla(), coordenacaoJuridicaDto.getSigla());
        
        final List<Assunto> assuntos = coordenacao.getAssuntos()
                                                  .stream()
                                                  .sorted(comparing(Assunto::getId))
                                                  .collect(toList());
        
        final List<AssuntoDTO> assuntosDto = coordenacaoJuridicaDto.getAssuntos()
                                                                   .stream()
                                                                   .sorted(comparing(AssuntoDTO::getId))
                                                                   .collect(toList());
        
        IntStream.range(0, 2)
                 .forEach(index -> {
                     AssuntoDTO dto = assuntosDto.get(index);
                     Assunto assunto = assuntos.get(index);
            
                     assertEquals(dto.getId(), assunto.getId());
                     assertEquals(dto.isAtivo(), assunto.getAtivo());
                     assertEquals(dto.getDescricao(), assunto.getDescricao());
                     assertEquals(dto.getPeso(), assunto.getPeso());
                 });
    }
    
    private CoordenacaoCreateUpdateDto createCoordenacaoJuridicaDto() {
        final var assuntoDto1 = new AssuntoDTO()
                                    .setId(1L)
                                    .setDescricao("Futebol")
                                    .setAtivo(Boolean.TRUE)
                                    .setPeso(1)
                                    .setDescricao("frequentemente usado em bares");
        final var assuntoDto2 = new AssuntoDTO()
                                    .setId(2L)
                                    .setDescricao("Cinema")
                                    .setAtivo(Boolean.FALSE)
                                    .setPeso(2)
                                    .setDescricao("frequentemente usado na falta de maior intimidade");
        return new CoordenacaoCreateUpdateDto()
                   .setId(1L)
                   .setCentena("423")
                   .setNome("Lack of coodernation")
                   .setSigla("OCU")
                   .setAssuntos(Set.of(assuntoDto1, assuntoDto2));
    }
}
