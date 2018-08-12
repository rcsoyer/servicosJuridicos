package com.rcsoyer.servicosjuridicos.service.dto;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rcsoyer.servicosjuridicos.domain.enumeration.FeriasLicensaTipo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO for the FeriasLicenca entity.
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "advogadoId")
public class FeriasLicencaDTO implements Serializable {

  private static final long serialVersionUID = 8253942326687428160L;

  private Long id;

  @NotNull
  private LocalDate dtInicio;

  @NotNull
  private LocalDate dtFim;

  @NotNull
  private FeriasLicensaTipo tipo;

  @NotNull
  private Long advogadoId;

  @JsonCreator
  public static FeriasLicencaDTO of(String json)
      throws JsonParseException, JsonMappingException, IOException {
    return JsonConverter.readValue(json, FeriasLicencaDTO.class);
  }
}
