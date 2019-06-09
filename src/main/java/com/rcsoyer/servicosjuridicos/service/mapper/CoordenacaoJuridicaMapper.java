package com.rcsoyer.servicosjuridicos.service.mapper;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.service.dto.AssuntoDTO;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoCreateUpdateDto;
import com.rcsoyer.servicosjuridicos.service.dto.CoordenacaoJuridicaDTO;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity CoordenacaoJuridica and its DTO CoordenacaoJuridicaDTO.
 */
@Mapper(componentModel = "spring")
public interface CoordenacaoJuridicaMapper {
    
    default CoordenacaoJuridica fromId(Long id) {
        return Optional.ofNullable(id)
                       .map(new CoordenacaoJuridica()::setId)
                       .orElse(null);
    }
    
    default CoordenacaoCreateUpdateDto toDto(final CoordenacaoJuridica coordenacao) {
        return new CoordenacaoCreateUpdateDto()
                   .setId(coordenacao.getId())
                   .setNome(coordenacao.getNome())
                   .setSigla(coordenacao.getSigla())
                   .setCentena(coordenacao.getCentena())
                   .setAssuntos(createAssuntosDto(coordenacao.getAssuntos()));
    }
    
    default <C extends CoordenacaoJuridicaDTO<?>> CoordenacaoJuridica toEntity(final C coordenacaoDto) {
        return new CoordenacaoJuridica()
                   .setId(coordenacaoDto.getId())
                   .setNome(coordenacaoDto.getNome())
                   .setSigla(coordenacaoDto.getSigla())
                   .setCentena(coordenacaoDto.getCentena())
                   .addAssuntos(createAssuntos(coordenacaoDto.getAssuntos()));
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
    
    private Set<AssuntoDTO> createAssuntosDto(final Set<Assunto> assuntos) {
        return assuntos.stream()
                       .map(assunto -> new AssuntoDTO()
                                           .setAtivo(assunto.getAtivo())
                                           .setDescricao(assunto.getDescricao())
                                           .setPeso(assunto.getPeso())
                                           .setId(assunto.getId()))
                       .collect(toSet());
    }
    
}
