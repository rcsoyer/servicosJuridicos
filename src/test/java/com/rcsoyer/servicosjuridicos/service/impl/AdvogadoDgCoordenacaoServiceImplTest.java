package com.rcsoyer.servicosjuridicos.service.impl;

import static com.rcsoyer.servicosjuridicos.domain.enumeration.RangeDgCoordenacao.INCLUSIVE;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.repository.AdvogadoDgCoordenacaoRepository;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AdvogadoDgCoordenacaoMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class AdvogadoDgCoordenacaoServiceImplTest {
    
    @Mock
    private AdvogadoDgCoordenacaoMapper mapper;
    
    @Mock
    private AdvogadoDgCoordenacaoRepository repository;
    
    @InjectMocks
    private AdvogadoDgCoordenacaoServiceImpl service;
    
    private AdvogadoDgCoordenacao model;
    private AdvogadoDgCoordenacaoDTO dto;
    private AdvogadoDgCoordenacao savedAdvogado;
    
    @BeforeEach
    void setUp() {
        this.model = new AdvogadoDgCoordenacao().setId(1L)
                                                .setAdvogado(new Advogado())
                                                .setCoordenacao(new CoordenacaoJuridica())
                                                .setDgPessoalInicio(0)
                                                .setDgDupla(1)
                                                .setDgPessoalFim(2)
                                                .setRangeDgCoordenacao(INCLUSIVE);
        this.savedAdvogado = new AdvogadoDgCoordenacao().setId(1L)
                                                        .setAdvogado(new Advogado())
                                                        .setCoordenacao(new CoordenacaoJuridica())
                                                        .setDgPessoalInicio(0)
                                                        .setDgDupla(1)
                                                        .setDgPessoalFim(2)
                                                        .setRangeDgCoordenacao(INCLUSIVE);
        this.dto = new AdvogadoDgCoordenacaoDTO().setId(1L)
                                                 .setAdvogado(1L)
                                                 .setCoordenacao(1L)
                                                 .setDgDupla(0)
                                                 .setDgPessoalFim(1)
                                                 .setDgPessoalInicio(2)
                                                 .setRangeDgCoordenacao(INCLUSIVE);
    }
    
    @Test
    void save() {
        when(mapper.toEntity(dto)).thenReturn(model);
        when(repository.save(model)).thenReturn(savedAdvogado);
        when(mapper.toDto(savedAdvogado)).thenReturn(dto);
        
        var savedDto = service.save(dto);
        
        assertEquals(savedDto, dto);
        verify(mapper, times(1)).toDto(any(AdvogadoDgCoordenacao.class));
        verify(repository, times(1)).save(any());
        verify(mapper, times(1)).toEntity(any(AdvogadoDgCoordenacaoDTO.class));
        verifyNoMoreInteractions(mapper, repository);
    }
    
    @Test
    void findOne() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new AdvogadoDgCoordenacao()));
        when(mapper.toDto(any(AdvogadoDgCoordenacao.class)))
            .thenReturn(new AdvogadoDgCoordenacaoDTO());
        
        var dtoOptional = service.findOne(anyLong());
        
        assertTrue(dtoOptional.isPresent());
        verify(repository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(any(AdvogadoDgCoordenacao.class));
        verifyNoMoreInteractions(mapper, repository);
    }
    
    @Test
    void findOne_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        
        var dtoOptional = service.findOne(anyLong());
        
        assertTrue(dtoOptional.isEmpty());
        verify(repository, times(1)).findById(anyLong());
        verify(mapper, never()).toDto(any(AdvogadoDgCoordenacao.class));
        verifyNoMoreInteractions(mapper, repository);
    }
    
    @Test
    void delete() {
        service.delete(anyLong());
        
        verify(repository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(repository);
    }
    
    @Test
    void findByParams() {
        var dgCoordenacoes = new PageImpl<>(singletonList(savedAdvogado));
        
        PageRequest pageable = PageRequest.of(0, 10);
        
        when(mapper.toEntity(dto)).thenReturn(savedAdvogado);
        when(repository.query(savedAdvogado, pageable)).thenReturn(dgCoordenacoes);
        when(mapper.toDto(any(AdvogadoDgCoordenacao.class))).thenReturn(any(AdvogadoDgCoordenacaoDTO.class));
        
        service.seekByParams(dto, pageable);
        
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).query(savedAdvogado, pageable);
        verify(mapper, times(1)).toDto(any(AdvogadoDgCoordenacao.class));
        verifyNoMoreInteractions(repository, mapper);
    }
}
