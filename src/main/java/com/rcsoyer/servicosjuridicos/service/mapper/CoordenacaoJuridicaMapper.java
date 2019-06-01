package com.rcsoyer.servicosjuridicos.service.mapper;

import static java.util.stream.Collectors.toList;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoCreateUpdateDto;
import com.rcsoyer.servicosjuridicos.service.dto.QueryParamsCoordenacao;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity CoordenacaoJuridica and its DTO CoordenacaoJuridicaDTO.
 */
@Mapper(componentModel = "spring", uses = AssuntoMapper.class)
public interface CoordenacaoJuridicaMapper extends EntityMapper<CoordenacaoCreateUpdateDto, CoordenacaoJuridica> {
    
    default CoordenacaoJuridica fromId(Long id) {
        return Optional.ofNullable(id)
                       .map(new CoordenacaoJuridica()::setId)
                       .orElse(null);
    }
    
    default CoordenacaoJuridica toEntity(final CoordenacaoCreateUpdateDto coordenacaoDto) {
        return new CoordenacaoJuridica()
                   .setNome(coordenacaoDto.getNome())
                   .setSigla(coordenacaoDto.getSigla())
                   .setCentena(coordenacaoDto.getCentena())
                   .setId(coordenacaoDto.getId())
                   .addAssuntos(createAssuntos(coordenacaoDto.getAssuntos()));
    }
    
    default CoordenacaoJuridica toEntity(final QueryParamsCoordenacao queryParams) {
        return new CoordenacaoJuridica()
                   .setNome(queryParams.getNome())
                   .setSigla(queryParams.getSigla())
                   .setCentena(queryParams.getCentena())
                   .setId(queryParams.getId())
                   .addAssuntos(createAssuntos(queryParams.getAssuntos()));
    }
    
    private List<Assunto> createAssuntos(final Set<AssuntoDTO> assuntosDto) {
        return assuntosDto.stream()
                          .map(assuntoDto -> new Assunto()
                                                 .setAtivo(assuntoDto.isAtivo())
                                                 .setDescricao(assuntoDto.getDescricao())
                                                 .setPeso(assuntoDto.getPeso())
                                                 .setId(assuntoDto.getId()))
                          .collect(toList());
    }
    
}
