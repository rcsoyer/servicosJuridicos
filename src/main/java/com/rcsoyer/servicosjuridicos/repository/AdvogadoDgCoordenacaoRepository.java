package com.rcsoyer.servicosjuridicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rcsoyer.servicosjuridicos.domain.AdvogadoDgCoordenacao;


/**
 * Spring Data JPA repository for the AdvogadoDgCoordenacao entity.
 */
@Repository
public interface AdvogadoDgCoordenacaoRepository extends JpaRepository<AdvogadoDgCoordenacao, Long> {

}
