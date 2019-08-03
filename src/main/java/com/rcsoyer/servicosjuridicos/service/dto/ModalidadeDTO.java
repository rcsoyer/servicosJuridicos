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

/**
 * A DTO for the Modalidade entity.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class ModalidadeDTO implements Serializable {
    
    private static final long serialVersionUID = 8196822569253387907L;
    
    @Min(1L)
    private Long id;
    
    @NotBlank
    @Size(max = 60)
    private String descricao;
    
}
