package com.rcsoyer.servicosjuridicos.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.io.IOException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
@ToString(exclude = "pageable")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageableDTO {
    
    @Min(0)
    @NotNull
    private Integer page;
    
    @Min(0)
    @NotNull
    private Integer size;
    
    @NotEmpty
    private String[] sort;
    
    @Getter(AccessLevel.PUBLIC)
    @JsonProperty(access = Access.READ_ONLY)
    private Pageable pageable;
    
    @JsonCreator
    public static PageableDTO of(String json) throws IOException {
        var dto = JsonConverter.readValue(json, PageableDTO.class);
        String[] order = dto.getSort()[0].split(",");
        var direction = Sort.Direction.fromString(order[1]);
        var sort = Sort.by(direction, order[0]);
        dto.setPageable(PageRequest.of(dto.getPage(), dto.getSize(), sort));
        return dto;
    }
}

