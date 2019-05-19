package com.rcsoyer.servicosjuridicos.service.dto;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.br.CPF;

/**
 * A DTO for the Advogado entity.
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "cpf"})
public final class AdvogadoDTO implements Serializable {
    
    private static final long serialVersionUID = 185125590770954216L;
    
    private Long id;
    
    @NotBlank
    @Size(max = 80)
    private String nome;
    
    @NotBlank
    @CPF(message = "CPF inválido")
    private String cpf;
    
    @Min(1)
    private Integer ramal;
}

