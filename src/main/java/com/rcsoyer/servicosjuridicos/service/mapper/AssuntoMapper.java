package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Assunto and its DTO AssuntoDTO.
 */
@Mapper(componentModel = "spring")
public interface AssuntoMapper extends EntityMapper<AssuntoDTO, Assunto> {

}
