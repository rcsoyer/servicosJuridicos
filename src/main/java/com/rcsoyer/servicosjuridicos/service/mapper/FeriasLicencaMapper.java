package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.*;
import com.rcsoyer.servicosjuridicos.service.dto.FeriasLicencaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FeriasLicenca and its DTO FeriasLicencaDTO.
 */
@Mapper(componentModel = "spring", uses = {AdvogadoMapper.class})
public interface FeriasLicencaMapper extends EntityMapper<FeriasLicencaDTO, FeriasLicenca> {

    @Mapping(source = "advogado.id", target = "advogadoId")
    FeriasLicencaDTO toDto(FeriasLicenca feriasLicenca);

    @Mapping(source = "advogadoId", target = "advogado")
    FeriasLicenca toEntity(FeriasLicencaDTO feriasLicencaDTO);

    default FeriasLicenca fromId(Long id) {
        if (id == null) {
            return null;
        }
        FeriasLicenca feriasLicenca = new FeriasLicenca();
        feriasLicenca.setId(id);
        return feriasLicenca;
    }
}
