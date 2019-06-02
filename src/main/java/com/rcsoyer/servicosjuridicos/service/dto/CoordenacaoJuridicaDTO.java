package com.rcsoyer.servicosjuridicos.service.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for the CoordenacaoJuridica entity.
 */
@Getter
@ToString
@EqualsAndHashCode(of = {"id", "sigla", "nome"})
public abstract class CoordenacaoJuridicaDTO<T> {
    
    protected Long id;
    
    @NotBlank
    @Size(max = 6)
    protected String sigla;
    
    @NotBlank
    @Size(max = 50)
    protected String nome;
    
    @NotBlank
    @Pattern(regexp = "[\\d]{3}")
    protected String centena;
    
    @NotEmpty
    protected Set<AssuntoDTO> assuntos;
    
    CoordenacaoJuridicaDTO() {
        this.assuntos = new HashSet<>(0);
    }
    
    protected abstract CoordenacaoJuridicaDTO setId(Long id);
    
    protected abstract CoordenacaoJuridicaDTO setSigla(String sigla);
    
    protected abstract CoordenacaoJuridicaDTO setNome(String nome);
    
    protected abstract CoordenacaoJuridicaDTO setCentena(String centena);
    
    protected abstract CoordenacaoJuridicaDTO setAssuntos(Set<T> assuntos);
    
}
