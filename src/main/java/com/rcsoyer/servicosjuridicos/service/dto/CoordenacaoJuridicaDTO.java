package com.rcsoyer.servicosjuridicos.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
@ToString
@Accessors(chain = true)
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(of = {"id", "sigla", "nome"})
public class CoordenacaoJuridicaDTO implements Serializable {
    
    private static final long serialVersionUID = 6494069770673909164L;
    
    @JsonView(AssuntoViews.All.class)
    private Long id;
    
    @NotBlank
    @Size(max = 6)
    @JsonView(AssuntoViews.All.class)
    private String sigla;
    
    @NotBlank
    @Size(max = 50)
    @JsonView(AssuntoViews.All.class)
    private String nome;
    
    @NotBlank
    @Pattern(regexp = "[\\d]{3}")
    @JsonView(AssuntoViews.All.class)
    private String centena;
    
    @NotEmpty
    @JsonView(AssuntoViews.CreateUpdate.class)
    private Set<AssuntoDTO> assuntos;
    
    @JsonView(AssuntoViews.QueryParams.class)
    private Set<Long> assuntosIds = Collections.emptySet();
    
    public interface AssuntoViews {
        
        interface All {
        
        }
        
        interface CreateUpdate extends All {
        
        }
        
        interface QueryParams extends All {
        
        }
    }
    
}
