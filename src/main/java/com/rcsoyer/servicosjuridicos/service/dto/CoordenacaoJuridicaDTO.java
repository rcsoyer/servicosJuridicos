package com.rcsoyer.servicosjuridicos.service.dto;

import java.io.Serializable;
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
@EqualsAndHashCode(of = {"id", "sigla", "nome"})
public class CoordenacaoJuridicaDTO implements Serializable {
    
    private static final long serialVersionUID = 6494069770673909164L;
    
    private Long id;
    
    @NotBlank
    @Size(max = 6)
    private String sigla;
    
    @NotBlank
    @Size(max = 50)
    private String nome;
    
    @NotBlank
    @Pattern(regexp = "[\\d]{3}")
    private String centena;
    
    @NotEmpty
    private Set<AssuntoDTO> assuntos;
}
