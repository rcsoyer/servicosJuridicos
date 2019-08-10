package com.rcsoyer.servicosjuridicos.service.dto;

import com.rcsoyer.servicosjuridicos.service.dto.validationgroups.ProcessoJudicialOnCreate;
import com.rcsoyer.servicosjuridicos.service.dto.validationgroups.ProcessoJudicialOnUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * A DTO for the ProcessoJudicial entity.
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "numero"})
@ToString(exclude = {"assunto", "modalidade", "advogado"})
public final class ProcessoJudicialDTO implements Serializable {
    
    private static final long serialVersionUID = 4412327118758497711L;
    
    @Null(groups = ProcessoJudicialOnCreate.class)
    @NotNull(groups = ProcessoJudicialOnUpdate.class)
    @Min(value = 1L, groups = ProcessoJudicialOnUpdate.class)
    private Long id;
    
    @NotBlank(groups = {ProcessoJudicialOnCreate.class, ProcessoJudicialOnUpdate.class})
    @Size(min = 20, max = 20, groups = {ProcessoJudicialOnCreate.class, ProcessoJudicialOnUpdate.class})
    private String numero;
    
    private LocalDateTime prazoFinal;
    
    @Null(groups = ProcessoJudicialOnCreate.class)
    private LocalDateTime dtAtribuicao;
    
    @Null(groups = {ProcessoJudicialOnCreate.class, ProcessoJudicialOnUpdate.class})
    private LocalDateTime dtInicio;
    
    private LocalDateTime dtConclusao;
    
    private AdvogadoDTO advogado;
    
    @NotNull
    private AssuntoDTO assunto;
    
    @NotNull
    private ModalidadeDTO modalidade;
    
}
