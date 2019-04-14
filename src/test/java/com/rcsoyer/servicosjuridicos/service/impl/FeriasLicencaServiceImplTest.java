package com.rcsoyer.servicosjuridicos.service.impl;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import com.rcsoyer.servicosjuridicos.repository.FeriasLicencaRepository;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.FeriasLicencaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class FeriasLicencaServiceImplTest {
    
    @Mock
    private FeriasLicencaMapper mapper;
    
    @Mock
    private FeriasLicencaRepository repository;
    
    @InjectMocks
    private FeriasLicencaServiceImpl service;
    
    private FeriasLicenca domain;
    private FeriasLicencaDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new FeriasLicencaDTO().setId(1L);
        domain = new FeriasLicenca();
    }
    
    @Test
    void save() {
        when(mapper.toEntity(dto)).thenReturn(domain);
        domain.setId(1L);
        when(repository.save(domain)).thenReturn(domain);
        when(mapper.toDto(domain)).thenReturn(dto.setId(1L));
        service.save(dto);
        verify(mapper, times(1)).toEntity(any(FeriasLicencaDTO.class));
        verify(repository, times(1)).save(any(FeriasLicenca.class));
        verify(mapper, times(1)).toDto(any(FeriasLicenca.class));
        verifyNoMoreInteractions(mapper, repository);
    }
    
    @Test
    void findAll() {
        var pageable = PageRequest.of(0, 10);
        var feriasLicencaPage = new PageImpl<FeriasLicenca>(emptyList());
        when(repository.findAll(pageable)).thenReturn(feriasLicencaPage);
        when(mapper.toDto(any(FeriasLicenca.class))).thenReturn(dto);
        service.findAll(pageable);
        verify(repository, times(1)).findAll(pageable);
    }
    
    @Test
    void findOne() {
    }
    
    @Test
    void delete() {
    }
}
