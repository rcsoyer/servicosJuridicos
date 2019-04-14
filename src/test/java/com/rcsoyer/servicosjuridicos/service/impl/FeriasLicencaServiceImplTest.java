package com.rcsoyer.servicosjuridicos.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import com.rcsoyer.servicosjuridicos.repository.FeriasLicencaRepository;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;
import com.rcsoyer.servicosjuridicos.service.mapper.FeriasLicencaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class FeriasLicencaServiceImplTest {
    
    @Mock
    private FeriasLicencaMapper mapper;
    
    @Mock
    private FeriasLicencaRepository repository;
    
    @InjectMocks
    private FeriasLicencaServiceImpl service;
    
    @Test
    void save() {
        var dto = new FeriasLicencaDTO().setId(1L);
        var domain = new FeriasLicenca();
        when(mapper.toEntity(dto)).thenReturn(domain);
        domain.setId(1L);
        when(repository.save(domain)).thenReturn(domain);
        when(mapper.toDto(domain)).thenReturn(any(FeriasLicencaDTO.class));
        service.save(dto);
        verify(mapper, times(1)).toEntity(any(FeriasLicencaDTO.class));
        verify(repository, times(1)).save(any(FeriasLicenca.class));
    }
    
    @Test
    void findAll() {
    }
    
    @Test
    void findOne() {
    }
    
    @Test
    void delete() {
    }
}
