package com.rcsoyer.servicosjuridicos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryParams<T> {
    
    Page<T> findByParams(T params, Pageable pageable);
}
