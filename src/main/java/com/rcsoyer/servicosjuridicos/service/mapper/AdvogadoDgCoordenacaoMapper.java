package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.*;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdvogadoDgCoordenacao and its DTO AdvogadoDgCoordenacaoDTO.
 */
@Mapper(componentModel = "spring", uses = {AdvogadoMapper.class, CoordenacaoJuridicaMapper.class})
public interface AdvogadoDgCoordenacaoMapper extends EntityMapper<AdvogadoDgCoordenacaoDTO, AdvogadoDgCoordenacao> {

    @Mapping(source = "advogado.id", target = "advogadoId")
    @Mapping(source = "coordenacao.id", target = "coordenacaoId")
    AdvogadoDgCoordenacaoDTO toDto(AdvogadoDgCoordenacao advogadoDgCoordenacao);

    @Mapping(source = "advogadoId", target = "advogado")
    @Mapping(source = "coordenacaoId", target = "coordenacao")
    AdvogadoDgCoordenacao toEntity(AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO);

    default AdvogadoDgCoordenacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdvogadoDgCoordenacao advogadoDgCoordenacao = new AdvogadoDgCoordenacao();
        advogadoDgCoordenacao.setId(id);
        return advogadoDgCoordenacao;
    }
}
