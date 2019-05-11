package com.rcsoyer.servicosjuridicos.service.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.repository.assunto.AssuntoRepository;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AssuntoMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


@ExtendWith(MockitoExtension.class)
class AssuntoServiceImplTest {
    
    private Assunto assunto;
    private AssuntoDTO dto;
    
    @Mock
    private AssuntoMapper mapper;
    
    @Mock
    private AssuntoRepository repository;
    
    @InjectMocks
    private AssuntoServiceImpl service;
    
    @BeforeEach
    void setUp() {
        this.assunto = new Assunto().setAtivo(true)
                                    .setDescricao("the dark side of the moon")
                                    .setPeso(1);
        this.dto = new AssuntoDTO().setAtivo(true)
                                   .setDescricao("the dark side of the moon")
                                   .setPeso(1);
    }
    
    @Test
    void save() {
        when(mapper.toEntity(dto)).thenReturn(assunto);
        when(repository.save(assunto)).thenReturn(assunto.setId(1L));
        when(mapper.toDto(assunto)).thenReturn(dto.setId(1L));
        var savedDto = service.save(dto);
        assertEquals(savedDto, dto);
        verify(mapper, times(1)).toDto(any(Assunto.class));
        verify(repository, times(1)).save(any());
        verify(mapper, times(1)).toEntity(any(AssuntoDTO.class));
        verifyNoMoreInteractions(mapper, repository);
    }
    
    @Test
    void findAll() {
        var assunto1 = new Assunto().setId(1L).setDescricao("1");
        var assunto2 = new Assunto().setId(2L).setDescricao("2");
        var dto1 = new AssuntoDTO().setId(1L).setDescricao("1");
        var dto2 = new AssuntoDTO().setId(2L).setDescricao("2");
        var pageable = PageRequest.of(0, 10);
        var assuntos = new PageImpl<>(asList(assunto1, assunto2));
        when(repository.findAll(pageable)).thenReturn(assuntos);
        when(mapper.toDto(assunto1)).thenReturn(dto1);
        // when(mapper.toDto(assunto2)).thenReturn(dto2);
        Page<AssuntoDTO> page = service.findAll(pageable);
        assertEquals(assuntos.getContent().get(0).getId(), page.getContent().get(0).getId());
        assertEquals(assuntos.getContent().get(0).getDescricao(), page.getContent().get(0).getDescricao());
        //assertEquals(assuntos.getContent().get(1).getId(), page.getContent().get(1).getId());
        //assertEquals(assuntos.getContent().get(1).getDescricao(), page.getContent().get(1).getDescricao());
    }
    
    @Test
    void findOne() {
        var assunto = Optional.of(new Assunto().setId(1L));
        
        when(repository.findById(1L)).thenReturn(assunto);
        when(mapper.toDto(assunto.get())).thenReturn(new AssuntoDTO().setId(1L));
        
        Optional<AssuntoDTO> assuntoDTO = service.findOne(1L);
        
        assertEquals(assunto.get().getId(), assuntoDTO.get().getId());
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).toDto(assunto.get());
        verifyNoMoreInteractions(repository, mapper);
    }
    
    @Test
    void delete() {
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(repository);
        verifyZeroInteractions(mapper);
    }
    
    @Test
    void findByParams() {
        var assunto = new Assunto().setId(1L);
        var dto = new AssuntoDTO().setId(1L);
        
        when(mapper.toEntity(dto)).thenReturn(assunto);
        
        var pageable = PageRequest.of(0, 10);
        
        when(repository.query(assunto, pageable)).thenReturn(new PageImpl<>(singletonList(assunto)));
        when(mapper.toDto(assunto)).thenReturn(dto.setDescricao("assunto 1"));
        
        Page<AssuntoDTO> result = service.findByParams(dto, pageable);
        
        assertTrue(result.getContent().contains(dto));
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).query(assunto, pageable);
        verify(mapper, times(1)).toEntity(dto);
        verifyNoMoreInteractions(repository, mapper);
    }
}
