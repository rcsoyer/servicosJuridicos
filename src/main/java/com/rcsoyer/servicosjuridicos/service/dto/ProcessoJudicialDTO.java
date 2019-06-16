package com.rcsoyer.servicosjuridicos.service.dto;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
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
    
    @Min(1L)
    private Long id;
    
    @NotBlank
    @Setter(AccessLevel.NONE)
    @Size(min = 20, max = 20)
    private String numero;
    
    @NotNull
    private LocalDate prazoFinal;
    
    private LocalDate dtAtribuicao;
    
    private LocalDate dtInicio;
    
    private LocalDate dtConclusao;
    
    private AdvogadoDTO advogado;
    
    @NotNull
    private AssuntoDTO assunto;
    
    @NotNull
    private ModalidadeDTO modalidade;
    
    @JsonCreator
    public static ProcessoJudicialDTO of(String json) throws IOException {
        return JsonConverter.readValue(json, ProcessoJudicialDTO.class);
    }
    
    public ProcessoJudicialDTO setNumero(String numero) {
        this.numero = trimToNull(numero);
        return this;
    }
}
