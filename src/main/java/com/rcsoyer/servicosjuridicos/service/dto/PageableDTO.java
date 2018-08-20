package com.rcsoyer.servicosjuridicos.service.dto;

import java.io.IOException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class PageableDTO {

  private Integer page;

  private Integer size;

  private String[] sort;

  @JsonProperty(access = Access.READ_ONLY)
  private Pageable pageable;

  @JsonCreator
  public static PageableDTO of(String json) throws IOException {
    PageableDTO dto = JsonConverter.readValue(json, PageableDTO.class);
    String[] order = dto.getSort()[0].split(",");
    Direction direction = Sort.Direction.fromString(order[1]);
    Sort sort = new Sort(direction, order[0]);
    dto.setPageable(PageRequest.of(dto.getPage(), dto.getSize(), sort));
    return dto;
  }
}
