package com.rcsoyer.servicosjuridicos.service.dto;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO for the CoordenacaoJuridica entity.
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "assuntos")
public class CoordenacaoJuridicaDTO implements Serializable {

  private static final long serialVersionUID = 6494069770673909164L;

  private Long id;

  @NotNull
  @Size(min = 1, max = 6)
  private String sigla;

  @NotNull
  @Size(min = 1, max = 50)
  private String nome;

  @Size(min = 3, max = 3)
  private String centena;

  @NotNull
  @Size(min = 1)
  private Set<AssuntoDTO> assuntos;

  @JsonCreator
  public static CoordenacaoJuridicaDTO of(String json) throws IOException {
    return JsonConverter.readValue(json, CoordenacaoJuridicaDTO.class);
  }
}
