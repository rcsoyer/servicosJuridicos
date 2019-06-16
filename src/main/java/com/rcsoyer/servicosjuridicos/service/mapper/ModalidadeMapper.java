package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Modalidade and its DTO ModalidadeDTO.
 */
@Mapper(componentModel = "spring")
public interface ModalidadeMapper extends EntityMapper<ModalidadeDTO, Modalidade> {

}
