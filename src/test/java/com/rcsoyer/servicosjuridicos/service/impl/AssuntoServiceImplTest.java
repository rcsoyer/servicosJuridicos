package com.rcsoyer.servicosjuridicos.service.impl;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.repository.AssuntoRepository;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AssuntoMapper;
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
class AssuntoServiceImplTest {
    
    @Mock
    private AssuntoMapper mapper;
    
    @Mock
    private AssuntoRepository repository;
    
    @InjectMocks
    private AssuntoServiceImpl service;
    
    @Test
    void save() {
        var assunto = new Assunto().setAtivo(true)
                                   .setDescricao("the dark side of the moon")
                                   .setPeso(1);
        var dto = new AssuntoDTO().setAtivo(true)
                                  .setDescricao("the dark side of the moon")
                                  .setPeso(1);
        
        when(mapper.toEntity(dto)).thenReturn(assunto);
        
        var savedAssunto = new Assunto().setId(1L)
                                        .setAtivo(true)
                                        .setDescricao("the dark side of the moon")
                                        .setPeso(1);
        
        when(repository.save(assunto)).thenReturn(savedAssunto);
        
        var savedDto = new AssuntoDTO().setAtivo(true)
                                       .setDescricao("the dark side of the moon")
                                       .setPeso(1);
        
        when(mapper.toDto(savedAssunto)).thenReturn(savedDto);
        
        assertEquals(service.save(dto), savedDto);
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).save(assunto);
        verify(mapper, times(1)).toDto(assunto.setId(1L));
        verifyNoMoreInteractions(mapper, repository);
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
    void seekByParams() {
        var assunto = new Assunto().setId(1L);
        var dto = new AssuntoDTO().setId(1L);
        
        when(mapper.toEntity(dto)).thenReturn(assunto);
        
        var pageable = PageRequest.of(0, 10);
        
        when(repository.findByAssunto(assunto, pageable)).thenReturn(new PageImpl<>(singletonList(assunto)));
        when(mapper.toDto(assunto)).thenReturn(dto.setDescricao("assunto 1"));
        
        Page<AssuntoDTO> result = service.seekByParams(dto, pageable);
        
        assertTrue(result.getContent().contains(dto));
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).findByAssunto(assunto, pageable);
        verify(mapper, times(1)).toEntity(dto);
        verifyNoMoreInteractions(repository, mapper);
    }
}
