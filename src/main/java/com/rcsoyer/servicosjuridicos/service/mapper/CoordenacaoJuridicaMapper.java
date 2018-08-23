package com.rcsoyer.servicosjuridicos.service.mapper;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;

/**
 * Mapper for the entity CoordenacaoJuridica and its DTO CoordenacaoJuridicaDTO.
 */
@Mapper(componentModel = "spring", uses = {AssuntoMapper.class})
public interface CoordenacaoJuridicaMapper extends EntityMapper<CoordenacaoJuridicaDTO, CoordenacaoJuridica> {

    @Mapping(target = "dgAdvogados", ignore = true)
    CoordenacaoJuridica toEntity(CoordenacaoJuridicaDTO coordenacaoJuridicaDTO);

    default CoordenacaoJuridica fromId(Long id) {
      return Optional.ofNullable(id)
                     .map(new CoordenacaoJuridica()::setId)
                     .orElse(null);
    }
}
