package com.rcsoyer.servicosjuridicos.service.mapper;

import java.util.Optional;
import org.mapstruct.Mapper;
import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import com.rcsoyer.servicosjuridicos.service.dto.ProcessoJudicialDTO;

/**
 * Mapper for the entity ProcessoJudicial and its DTO ProcessoJudicialDTO.
 */
@Mapper(componentModel = "spring",
    uses = {AssuntoMapper.class, ModalidadeMapper.class, AdvogadoMapper.class})
public interface ProcessoJudicialMapper
    extends EntityMapper<ProcessoJudicialDTO, ProcessoJudicial> {

  default ProcessoJudicial fromId(Long id) {
    return Optional.ofNullable(id)
                   .map(new ProcessoJudicial()::setId)
                   .orElseGet(ProcessoJudicial::new);
  }
}
