package com.rcsoyer.servicosjuridicos.repository;

import com.rcsoyer.servicosjuridicos.domain.AdvogadoDgCoordenacao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AdvogadoDgCoordenacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvogadoDgCoordenacaoRepository extends JpaRepository<AdvogadoDgCoordenacao, Long> {

}
