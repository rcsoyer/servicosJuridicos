package com.rcsoyer.servicosjuridicos.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import com.rcsoyer.servicosjuridicos.repository.ModalidadeRepository;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.ModalidadeMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ModalidadeServiceImplTest {
    
    @Mock
    private ModalidadeMapper mapper;
    
    @Mock
    private ModalidadeRepository repository;
    
    @InjectMocks
    private ModalidadeServiceImpl service;
    
    private ModalidadeDTO dto;
    private Modalidade modalidade;
    
    @BeforeEach
    void setUp() {
        this.dto = new ModalidadeDTO().setDescricao("descricacao 1");
        this.modalidade = new Modalidade().setDescricao("descricacao 1");
    }
    
    @Test
    void save() {
        final var persistedDto = new ModalidadeDTO()
                                     .setId(1L)
                                     .setDescricao(dto.getDescricao());
        final var persistedModalidade = new Modalidade()
                                            .setId(1L)
                                            .setDescricao(modalidade.getDescricao());
        
        when(mapper.toEntity(dto))
            .thenReturn(modalidade);
        when(repository.save(modalidade))
            .thenReturn(persistedModalidade);
        when(mapper.toDto(persistedModalidade))
            .thenReturn(persistedDto);
        
        final ModalidadeDTO result = service.save(dto);
        
        assertEquals(dto.getDescricao(), result.getDescricao());
        
        assertThat(result.getId())
            .isNotNull();
        
        verify(mapper).toEntity(dto);
        verify(repository).save(modalidade);
        verify(mapper).toDto(persistedModalidade);
        verifyNoMoreInteractions(mapper, repository);
    }
    
    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    void findOne() {
        final long id = 1L;
        dto.setId(id);
        modalidade.setId(id);
        
        when(repository.findById(id))
            .thenReturn(Optional.of(modalidade));
        
        when(mapper.toDto(modalidade))
            .thenReturn(dto);
        
        final Optional<ModalidadeDTO> result = service.findOne(id);
        
        assertEquals(dto, result.get());
        verify(repository).findById(id);
        verify(mapper).toDto(modalidade);
        verifyNoMoreInteractions(mapper, repository);
    }
    
    @Test
    void delete() {
    }
    
    @Test
    void findByParams() {
    }
}
