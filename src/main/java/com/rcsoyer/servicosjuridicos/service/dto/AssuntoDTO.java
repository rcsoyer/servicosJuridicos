package com.rcsoyer.servicosjuridicos.service.dto;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.IOException;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class AssuntoDTO implements Serializable {

    private static final long serialVersionUID = -3670483173597422016L;

    private Long id;

    @NotBlank
    @Size(min = 1, max = 70)
    @Setter(AccessLevel.NONE)
    private String descricao;

    @NotNull
    @Getter(value = AccessLevel.NONE)
    private Boolean ativo;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer peso;

    public Boolean isAtivo() {
        return ativo;
    }

    @JsonCreator
    public static AssuntoDTO of(String json) throws IOException {
        return JsonConverter.readValue(json, AssuntoDTO.class);
    }

    public AssuntoDTO setDescricao(String descricao) {
        this.descricao = trimToNull(descricao);
        return this;
    }
}
