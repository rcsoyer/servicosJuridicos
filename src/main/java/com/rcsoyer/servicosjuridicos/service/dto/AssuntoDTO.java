package com.rcsoyer.servicosjuridicos.service.dto;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "descricao"})
public class AssuntoDTO implements Serializable {
    
    private static final long serialVersionUID = -3670483173597422016L;
    
    private Long id;
    
    @NotBlank
    @Size(max = 70)
    private String descricao;
    
    @NotNull
    @Getter(value = AccessLevel.NONE)
    private Boolean ativo;
    
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer peso;
    
    public Boolean isAtivo() {
        return ativo;
    }
}
