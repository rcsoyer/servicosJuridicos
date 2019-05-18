package com.rcsoyer.servicosjuridicos.service.impl;

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
import com.rcsoyer.servicosjuridicos.repository.AdvogadoRepository;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AdvogadoMapper;
import java.util.List;
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
class AdvogadoServiceImplTest {
    
    @Mock
    private AdvogadoMapper mapper;
    
    @InjectMocks
    private AdvogadoServiceImpl service;
    
    @Mock
    private AdvogadoRepository repository;
    
    private Advogado advogado;
    private Advogado savedAdvogado;
    private AdvogadoDTO advogadoDTO;
    
    
    @BeforeEach
    void setUp() {
        this.advogadoDTO = new AdvogadoDTO().setId(1L)
                                            .setCpf("02376543289")
                                            .setNome("Banner")
                                            .setRamal(3423423);
        this.advogado = new Advogado().setId(1L)
                                      .setCpf("02376543289")
                                      .setNome("Banner")
                                      .setRamal(3423423);
        this.savedAdvogado = new Advogado().setId(1L)
                                           .setCpf("02376543289")
                                           .setNome("Banner")
                                           .setRamal(3423423);
    }
    
    @Test
    void save() {
        when(mapper.toEntity(advogadoDTO)).thenReturn(advogado);
        when(repository.save(advogado)).thenReturn(savedAdvogado);
        when(mapper.toDto(savedAdvogado)).thenReturn(advogadoDTO);
        
        var savedDto = service.save(advogadoDTO);
        
        assertEquals(savedDto, advogadoDTO);
        verify(mapper, times(1)).toDto(any(Advogado.class));
        verify(repository, times(1)).save(any());
        verify(mapper, times(1)).toEntity(any(AdvogadoDTO.class));
        verifyNoMoreInteractions(mapper, repository);
    }
    
    @Test
    void findOne() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Advogado()));
        when(mapper.toDto(any(Advogado.class))).thenReturn(new AdvogadoDTO());
        
        var dtoOptional = service.findOne(anyLong());
        
        assertTrue(dtoOptional.isPresent());
        verify(repository, times(1)).findById(anyLong());
        verify(mapper, times(1)).toDto(any(Advogado.class));
        verifyNoMoreInteractions(mapper, repository);
    }
    
    @Test
    void findOne_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        
        var dtoOptional = service.findOne(anyLong());
        
        assertTrue(dtoOptional.isEmpty());
        verify(repository, times(1)).findById(anyLong());
        verify(mapper, never()).toDto(any(Advogado.class));
        verifyNoMoreInteractions(mapper, repository);
    }
    
    @Test
    void delete() {
        service.delete(anyLong());
        
        verify(repository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(repository);
    }
    
    @Test
    void seekByParams() {
        var pageable = PageRequest.of(1, 2);
        var pgAdvogados = new PageImpl<>(List.of(advogado, savedAdvogado));
        
        when(mapper.toEntity(advogadoDTO)).thenReturn(advogado);
        when(repository.query(advogado, pageable)).thenReturn(pgAdvogados);
        when(mapper.toDto(any(Advogado.class))).thenReturn(new AdvogadoDTO());
        
        var seekingResult = service.seekByParams(advogadoDTO, pageable);
        
        assertEquals(seekingResult.getNumberOfElements(), pgAdvogados.getNumberOfElements());
        verify(mapper, times(1)).toEntity(any(AdvogadoDTO.class));
        verify(repository, times(1)).query(advogado, pageable);
        verify(mapper, times(2)).toDto(any(Advogado.class));
        verifyNoMoreInteractions(mapper, repository);
    }
}
