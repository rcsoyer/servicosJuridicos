package com.rcsoyer.servicosjuridicos.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordenacaoJuridicaDTOTest {
    
    private CoordenacaoJuridicaDTO dto;
    
    @BeforeEach
    void setUp() {
        this.dto = new CoordenacaoJuridicaDTO().setId(1L);
    }
    
    @Test
    void of() throws IOException {
        var json = new ObjectMapper().writeValueAsString(dto);
        assertEquals(CoordenacaoJuridicaDTO.of(json), dto);
    }
    
    @Test
    void setCentena() {
        dto.setCentena(" ");
        assertNull(dto.getCentena());
    }
    
    @Test
    void setSigla() {
        dto.setSigla(" ");
        assertNull(dto.getSigla());
    }
    
    @Test
    void setNome() {
        dto.setNome(" ");
        assertNull(dto.getNome());
    }
}
