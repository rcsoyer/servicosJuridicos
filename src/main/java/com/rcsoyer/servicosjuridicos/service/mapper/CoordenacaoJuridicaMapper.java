package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.*;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CoordenacaoJuridica and its DTO CoordenacaoJuridicaDTO.
 */
@Mapper(componentModel = "spring", uses = {AssuntoMapper.class})
public interface CoordenacaoJuridicaMapper extends EntityMapper<CoordenacaoJuridicaDTO, CoordenacaoJuridica> {


    @Mapping(target = "dgAdvogados", ignore = true)
    CoordenacaoJuridica toEntity(CoordenacaoJuridicaDTO coordenacaoJuridicaDTO);

    default CoordenacaoJuridica fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoordenacaoJuridica coordenacaoJuridica = new CoordenacaoJuridica();
        coordenacaoJuridica.setId(id);
        return coordenacaoJuridica;
    }
}
