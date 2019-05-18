package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDTO;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Advogado and its DTO AdvogadoDTO.
 */
@Mapper(componentModel = "spring")
public interface AdvogadoMapper extends EntityMapper<AdvogadoDTO, Advogado> {
    
    @Mapping(target = "processos", ignore = true)
    @Mapping(target = "feriasLicencas", ignore = true)
    @Mapping(target = "dgCoordenacoes", ignore = true)
    Advogado toEntity(AdvogadoDTO advogadoDTO);
    
    default Advogado fromId(Long id) {
        return Optional.ofNullable(id)
                       .map(new Advogado()::setId)
                       .orElseGet(Advogado::new);
    }
}
