package com.rcsoyer.servicosjuridicos.service.dto;

import java.io.IOException;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO for the Advogado entity.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class AdvogadoDTO implements Serializable {

  private static final long serialVersionUID = 185125590770954216L;

  private Long id;

  @NotNull
  @Size(min = 1, max = 80)
  private String nome;

  @NotNull
  @Size(min = 11, max = 11)
  private String cpf;

  private Integer ramal;

  @JsonCreator
  public static AdvogadoDTO of(String json)
      throws JsonParseException, JsonMappingException, IOException {
    return JsonConverter.readValue(json, AdvogadoDTO.class);
  }
}
