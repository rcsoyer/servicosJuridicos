package com.rcsoyer.servicosjuridicos.service.dto;

import static java.util.stream.Collectors.toSet;

import java.io.Serializable;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class QueryParamsCoordenacao extends CoordenacaoJuridicaDTO<Long> implements Serializable {
    
    private static final long serialVersionUID = -4989963223796988291L;
    
    @Override
    public QueryParamsCoordenacao setId(Long id) {
        this.id = id;
        return this;
    }
    
    @Override
    public QueryParamsCoordenacao setSigla(String sigla) {
        this.sigla = sigla;
        return this;
    }
    
    @Override
    public QueryParamsCoordenacao setNome(String nome) {
        this.nome = nome;
        return this;
    }
    
    @Override
    public QueryParamsCoordenacao setCentena(String centena) {
        this.centena = centena;
        return this;
    }
    
    @Override
    public QueryParamsCoordenacao setAssuntos(final Set<Long> assuntos) {
        this.assuntos = assuntos.stream()
                                .map(assuntoId -> new AssuntoDTO().setId(assuntoId))
                                .collect(toSet());
        return this;
    }
}

