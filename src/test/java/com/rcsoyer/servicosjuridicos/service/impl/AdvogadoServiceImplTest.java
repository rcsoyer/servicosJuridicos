package com.rcsoyer.servicosjuridicos.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.repository.advogado.AdvogadoRepository;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.AdvogadoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdvogadoServiceImplTest {
    
    @Mock
    private AdvogadoMapper mapper;
    
    @InjectMocks
    private AdvogadoServiceImpl service;
    
    @Mock
    private AdvogadoRepository repository;
    
    private Advogado advogado;
    private AdvogadoDTO advogadoDTO;
    private Advogado savedAdvogado;
    
    @BeforeEach
    public void setUp() {
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
    public void save() {
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
    public void findAll() {
    }
    
    @Test
    public void findOne() {
    }
    
    @Test
    public void delete() {
    }
    
    @Test
    void findByParams() {
    }
}
