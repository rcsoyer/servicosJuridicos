package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import java.util.Optional;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Assunto and its DTO AssuntoDTO.
 */
@Mapper(componentModel = "spring")
public interface AssuntoMapper extends EntityMapper<AssuntoDTO, Assunto> {
    
    default Assunto fromId(Long id) {
        return Optional.ofNullable(id)
                       .map(new Assunto()::setId)
                       .orElseGet(Assunto::new);
    }
}
