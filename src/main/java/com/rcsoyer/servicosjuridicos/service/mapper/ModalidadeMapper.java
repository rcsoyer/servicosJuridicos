package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.*;
import com.rcsoyer.servicosjuridicos.service.dto.ModalidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Modalidade and its DTO ModalidadeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModalidadeMapper extends EntityMapper<ModalidadeDTO, Modalidade> {



    default Modalidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Modalidade modalidade = new Modalidade();
        modalidade.setId(id);
        return modalidade;
    }
}
