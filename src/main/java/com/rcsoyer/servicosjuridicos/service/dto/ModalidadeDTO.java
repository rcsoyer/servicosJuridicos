package com.rcsoyer.servicosjuridicos.service.dto;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.IOException;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO for the Modalidade entity.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class ModalidadeDTO implements Serializable {

    private static final long serialVersionUID = 8196822569253387907L;

    @Setter
    private Long id;

    @NotBlank
    @Size(min = 1, max = 60)
    private String descricao;

    @JsonCreator
    public static ModalidadeDTO of(String json) throws IOException {
        return JsonConverter.readValue(json, ModalidadeDTO.class);
    }

    public ModalidadeDTO setDescricao(String descricao) {
        this.descricao = trimToNull(descricao);
        return this;
    }
}
