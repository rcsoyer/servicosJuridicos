package com.rcsoyer.servicosjuridicos.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.IOException;
import java.io.Serializable;
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
public class AdvogadoDTO implements Serializable {
    
    private static final long serialVersionUID = 185125590770954216L;
    
    private Long id;
    
    @NotBlank
    @Size(max = 80)
    private String nome;
    
    @NotBlank
    @CPF(message = "CPF inválido")
    private String cpf;
    
    private Integer ramal;
    
    @JsonCreator
    public static AdvogadoDTO of(String json) throws IOException {
        return JsonConverter.readValue(json, AdvogadoDTO.class);
    }
}

