package com.rcsoyer.servicosjuridicos.service.dto;

import java.io.Serializable;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CoordenacaoCreateUpdateDto extends CoordenacaoJuridicaDTO<AssuntoDTO> implements Serializable {
    
    private static final long serialVersionUID = 4610080190846912510L;
    
    @Override
    public CoordenacaoCreateUpdateDto setId(Long id) {
        this.id = id;
        return this;
    }
    
    @Override
    public CoordenacaoCreateUpdateDto setSigla(String sigla) {
        this.sigla = sigla;
        return this;
    }
    
    @Override
    public CoordenacaoCreateUpdateDto setNome(String nome) {
        this.nome = nome;
        return this;
    }
    
    @Override
    public CoordenacaoCreateUpdateDto setCentena(String centena) {
        this.centena = centena;
        return this;
    }
    
    @Override
    public CoordenacaoCreateUpdateDto setAssuntos(final Set<AssuntoDTO> assuntos) {
        this.assuntos = assuntos;
        return this;
    }
}
