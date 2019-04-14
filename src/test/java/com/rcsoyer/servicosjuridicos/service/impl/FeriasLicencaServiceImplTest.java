package com.rcsoyer.servicosjuridicos.service.impl;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import com.rcsoyer.servicosjuridicos.repository.FeriasLicencaRepository;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.FeriasLicencaMapper;
import java.util.Optional;
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
        domain = new FeriasLicenca().setId(1L);
    }
    
    @Test
    void save() {
        when(mapper.toEntity(dto)).thenReturn(domain);
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
        verifyNoMoreInteractions(repository, mapper);
    }
    
    @Test
    void findOne() {
        long numberOfTheBeast = 666L;
        var optDto = Optional.of(new FeriasLicencaDTO().setId(numberOfTheBeast));
        var optFeriasLicenca = Optional.of(new FeriasLicenca().setId(numberOfTheBeast));
        when(repository.findById(numberOfTheBeast)).thenReturn(optFeriasLicenca);
        when(mapper.toDto(optFeriasLicenca.get())).thenReturn(optDto.get());
        Optional<FeriasLicencaDTO> oneFounnded = service.findOne(numberOfTheBeast);
        verify(repository, times(1)).findById(numberOfTheBeast);
        verify(mapper, times(1)).toDto(optFeriasLicenca.get());
        verifyNoMoreInteractions(mapper, repository);
        assertEquals(optFeriasLicenca.get().getId().longValue(), oneFounnded.get().getId().longValue());
    }
    
    @Test
    void delete() {
        long numberOfTheBeast = 666L;
        service.delete(numberOfTheBeast);
        verify(repository, times(1)).deleteById(numberOfTheBeast);
        verifyZeroInteractions(mapper);
        verifyNoMoreInteractions(repository);
    }
    
    @Test
    void findByParams() {
        var pageable = PageRequest.of(0, 10);
        var dtoPage = new PageImpl<>(singletonList(dto));
        var feriasLicencaPage = new PageImpl<>(singletonList(domain));
        when(mapper.toEntity(dto)).thenReturn(domain);
        when(repository.query(domain, pageable)).thenReturn(feriasLicencaPage);
        service.findByParams(dto, pageable);
        assertEquals(feriasLicencaPage.getSize(), dtoPage.getSize());
        assertEquals(feriasLicencaPage.getContent().get(0).getId(), feriasLicencaPage.getContent().get(0).getId());
        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).query(domain, pageable);
        verify(mapper, times(1)).toDto(domain);
        verifyNoMoreInteractions(repository, mapper);
    }
}
