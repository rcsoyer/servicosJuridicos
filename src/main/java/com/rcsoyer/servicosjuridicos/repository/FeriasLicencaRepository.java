package com.rcsoyer.servicosjuridicos.repository;

import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FeriasLicenca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeriasLicencaRepository extends JpaRepository<FeriasLicenca, Long> {

}
