package com.rcsoyer.servicosjuridicos.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rcsoyer.servicosjuridicos.domain.enumeration.FeriasLicensaTipo;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A DTO for the FeriasLicenca entity.
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
public class FeriasLicencaDTO implements Serializable {
    
    private static final long serialVersionUID = 8253942326687428160L;
    
    private Long id;
    
    @NotNull
    private LocalDate dtInicio;
    
    @NotNull
    private LocalDate dtFim;
    
    @NotNull
    private FeriasLicensaTipo tipo;
    
    @Min(1)
    @NotNull
    private Long advogadoId;
    
    @JsonCreator
    public static FeriasLicencaDTO of(String json) throws IOException {
        return JsonConverter.readValue(json, FeriasLicencaDTO.class);
    }
}
