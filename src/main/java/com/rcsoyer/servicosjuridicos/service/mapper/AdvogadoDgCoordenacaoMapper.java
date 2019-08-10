package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.service.dto.AdvogadoDgCoordenacaoDTO;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity AdvogadoDgCoordenacao and its DTO AdvogadoDgCoordenacaoDTO.
 */
@Mapper(componentModel = "spring", uses = {AdvogadoMapper.class, CoordenacaoJuridicaMapper.class})
public interface AdvogadoDgCoordenacaoMapper extends EntityMapper<AdvogadoDgCoordenacaoDTO, AdvogadoDgCoordenacao> {
    
    @Mapping(source = "advogado.id", target = "advogado")
    @Mapping(source = "coordenacao.id", target = "coordenacao")
    AdvogadoDgCoordenacaoDTO toDto(AdvogadoDgCoordenacao advogadoDgCoordenacao);
    
    @Mapping(source = "advogado", target = "advogado")
    @Mapping(source = "coordenacao", target = "coordenacao")
    AdvogadoDgCoordenacao toEntity(AdvogadoDgCoordenacaoDTO advogadoDgCoordenacaoDTO);
    
    default AdvogadoDgCoordenacao fromId(Long id) {
        return Optional.ofNullable(id)
                       .map(new AdvogadoDgCoordenacao()::setId)
                       .orElse(null);
    }
}
