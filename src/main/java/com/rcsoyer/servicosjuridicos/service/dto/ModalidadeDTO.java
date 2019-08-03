package com.rcsoyer.servicosjuridicos.service.dto;

import com.rcsoyer.servicosjuridicos.service.dto.validationgroups.ModalidadeOnCreate;
import com.rcsoyer.servicosjuridicos.service.dto.validationgroups.ModalidadeOnUpdate;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO for the Modalidade entity.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public final class ModalidadeDTO implements Serializable {
    
    private static final long serialVersionUID = 8196822569253387907L;
    
    @Min(1L)
    @Null(groups = ModalidadeOnCreate.class)
    @NotNull(groups = ModalidadeOnUpdate.class)
    private Long id;
    
    @NotBlank
    @Size(max = 60)
    private String descricao;
    
}
