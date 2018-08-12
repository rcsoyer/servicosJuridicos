package com.rcsoyer.servicosjuridicos.service.dto;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO for the ProcessoJudicial entity.
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"assunto", "modalidade", "advogado"})
public class ProcessoJudicialDTO implements Serializable {

  private static final long serialVersionUID = 4412327118758497711L;

  private Long id;

  @NotNull
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
}
