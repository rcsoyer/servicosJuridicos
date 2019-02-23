package com.rcsoyer.servicosjuridicos.service.dto;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A DTO for the CoordenacaoJuridica entity.
 */
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@ToString(exclude = "assuntos")
public class CoordenacaoJuridicaDTO implements Serializable {
    
    private static final long serialVersionUID = 6494069770673909164L;
    
    private Long id;
    
    @NotEmpty
    @Size(max = 6)
    private String sigla;
    
    @NotEmpty
    @Size(max = 50)
    private String nome;
    
    @Size(min = 3, max = 3)
    private String centena;
    
    @NotEmpty
    private Set<AssuntoDTO> assuntos;
    
    @JsonCreator
    public static CoordenacaoJuridicaDTO of(String json) throws IOException {
        return JsonConverter.readValue(json, CoordenacaoJuridicaDTO.class);
    }
    
    public CoordenacaoJuridicaDTO setCentena(String centena) {
        this.centena = trimToNull(centena);
        return this;
    }
    
    public CoordenacaoJuridicaDTO setSigla(String sigla) {
        this.sigla = trimToNull(sigla);
        return this;
    }
    
    public CoordenacaoJuridicaDTO setNome(String nome) {
        this.nome = trimToNull(nome);
        return this;
    }
}
