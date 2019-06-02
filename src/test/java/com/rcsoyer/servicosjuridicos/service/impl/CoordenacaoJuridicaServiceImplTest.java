package com.rcsoyer.servicosjuridicos.service.impl;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.repository.CoordenacaoJuridicaRepository;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoCreateUpdateDto;
import com.rcsoyer.servicosjuridicos.service.dto.QueryParamsCoordenacao;
import com.rcsoyer.servicosjuridicos.service.mapper.CoordenacaoJuridicaMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CoordenacaoJuridicaServiceImplTest {
    
    @Mock
    private CoordenacaoJuridicaMapper coordenacaoMapper;
    
    @Mock
    private CoordenacaoJuridicaRepository coordenacaoRepository;
    
    @InjectMocks
    private CoordenacaoJuridicaServiceImpl coordenacaoService;
    
    @Test
    void save() {
        final var dtoParamInicio = new CoordenacaoCreateUpdateDto()
                                       .setNome("U.S.S Discovery")
                                       .setSigla("DISC")
                                       .setCentena("123");
        final var coordenacaoOfDto = new CoordenacaoJuridica()
                                         .setNome("U.S.S Discovery")
                                         .setSigla("DISC")
                                         .setCentena("123");
        final var coordenacaoFromSave = new CoordenacaoJuridica()
                                            .setId(1L)
                                            .setNome("U.S.S Discovery")
                                            .setSigla("DISC")
                                            .setCentena("123");
        final var dtoFromSave = new CoordenacaoCreateUpdateDto()
                                    .setId(1L)
                                    .setNome("U.S.S Discovery")
                                    .setSigla("DISC")
                                    .setCentena("123");
        
        when(coordenacaoMapper.toEntity(dtoParamInicio)).thenReturn(coordenacaoOfDto);
        when(coordenacaoRepository.save(coordenacaoOfDto)).thenReturn(coordenacaoFromSave);
        when(coordenacaoMapper.toDto(coordenacaoFromSave)).thenReturn(dtoFromSave);
        
        final CoordenacaoCreateUpdateDto result = coordenacaoService.save(dtoParamInicio);
        
        assertEquals(coordenacaoFromSave.getId(), result.getId());
        assertEquals(coordenacaoFromSave.getCentena(), result.getCentena());
        assertEquals(coordenacaoFromSave.getNome(), result.getNome());
        assertEquals(coordenacaoFromSave.getSigla(), result.getSigla());
        
        verify(coordenacaoMapper, times(1)).toEntity(dtoParamInicio);
        verify(coordenacaoRepository, times(1)).save(coordenacaoOfDto);
        verify(coordenacaoMapper, times(1)).toDto(coordenacaoFromSave);
        verifyNoMoreInteractions(coordenacaoMapper, coordenacaoRepository);
    }
    
    @Test
    void findOne() {
        final Long coordenacaoId = 666L;
        final var coordenacao = new CoordenacaoJuridica().setId(coordenacaoId);
        
        when(coordenacaoRepository.findById(coordenacaoId)).thenReturn(Optional.of(coordenacao));
        when(coordenacaoMapper.toDto(coordenacao)).thenReturn(new CoordenacaoCreateUpdateDto().setId(coordenacaoId));
        
        Optional<CoordenacaoCreateUpdateDto> one = coordenacaoService.findOne(coordenacaoId);
        
        assertEquals(coordenacaoId, one.get().getId());
        verify(coordenacaoRepository, times(1)).findById(coordenacaoId);
        verify(coordenacaoMapper, times(1)).toDto(coordenacao);
        verifyNoMoreInteractions(coordenacaoMapper, coordenacaoRepository);
    }
    
    @Test
    void delete() {
        final Long coordenacaoId = 666L;
        
        coordenacaoService.delete(coordenacaoId);
        
        verify(coordenacaoRepository, times(1)).deleteById(coordenacaoId);
        verifyNoMoreInteractions(coordenacaoRepository);
        verifyZeroInteractions(coordenacaoMapper);
    }
    
    @Test
    void findByParams() {
        final var queryParams = new QueryParamsCoordenacao()
                                    .setCentena("324")
                                    .setNome("Coordenacao trabalhista");
        final var coordenacaoOfDto = new CoordenacaoJuridica()
                                         .setCentena("324")
                                         .setNome("Coordenacao trabalhista");
        final var dtoResult = new CoordenacaoCreateUpdateDto()
                                  .setId(666L)
                                  .setSigla("ABC")
                                  .setCentena("324")
                                  .setNome("Coordenacao trabalhista");
        final var coordenacaoResult = new CoordenacaoJuridica()
                                          .setId(666L)
                                          .setSigla("ABC")
                                          .setCentena("324")
                                          .setNome("Coordenacao trabalhista");
        final var pageable = PageRequest.of(0, 10);
        final var pageResultFromRepositoy = new PageImpl<>(singletonList(coordenacaoResult), pageable, 1);
        
        when(coordenacaoMapper.toEntity(queryParams)).thenReturn(coordenacaoOfDto);
        when(coordenacaoRepository.query(coordenacaoOfDto, pageable)).thenReturn(pageResultFromRepositoy);
        when(coordenacaoMapper.toDto(coordenacaoResult)).thenReturn(dtoResult);
        
        final Page<CoordenacaoCreateUpdateDto> pageResult = coordenacaoService.seekByParams(queryParams, pageable);
        
        assertEquals(pageResultFromRepositoy.getSize(), pageResult.getSize());
        
        final CoordenacaoCreateUpdateDto coordenacaoDtoFromServiceResult = pageResult.getContent().get(0);
        
        assertEquals(coordenacaoResult.getId(), coordenacaoDtoFromServiceResult.getId());
        assertEquals(coordenacaoResult.getCentena(), coordenacaoDtoFromServiceResult.getCentena());
        assertEquals(coordenacaoResult.getNome(), coordenacaoDtoFromServiceResult.getNome());
        assertEquals(coordenacaoResult.getSigla(), coordenacaoDtoFromServiceResult.getSigla());
        
        verify(coordenacaoMapper, times(1)).toEntity(queryParams);
        verify(coordenacaoRepository, times(1)).query(coordenacaoOfDto, pageable);
        verify(coordenacaoMapper, times(1)).toDto(coordenacaoResult);
        verifyNoMoreInteractions(coordenacaoMapper, coordenacaoRepository);
    }
}
