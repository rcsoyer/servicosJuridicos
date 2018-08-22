package com.rcsoyer.servicosjuridicos.service.dto;

import java.io.IOException;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonCreator;
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
@EqualsAndHashCode(of = "id")
public class ModalidadeDTO implements Serializable {

  private static final long serialVersionUID = 8196822569253387907L;

  private Long id;

  @NotNull
  @Size(min = 1, max = 60)
  private String descricao;

  @JsonCreator
  public static ModalidadeDTO of(String json) throws IOException {
    return JsonConverter.readValue(json, ModalidadeDTO.class);
  }
}
