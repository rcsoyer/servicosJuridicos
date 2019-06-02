package com.rcsoyer.servicosjuridicos.service.dto;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueryParamsCoordenacaoTest {
    
    private QueryParamsCoordenacao queryParams;
    
    @BeforeEach
    void setUp() {
        this.queryParams = new QueryParamsCoordenacao()
                               .setId(1L)
                               .setCentena("645")
                               .setSigla("UYRWQ")
                               .setNome("Query filters");
    }
    
    @Test
    void setAssuntos() {
        assertTrue(queryParams.getAssuntos().isEmpty());
        
        final var assuntosIds = Set.of(1L, 2L);
        queryParams.setAssuntos(assuntosIds);
        
        assertEquals(assuntosIds.size(), queryParams.getAssuntos().size());
        
        final List<AssuntoDTO> assuntosOrdered = queryParams.getAssuntos()
                                                            .stream()
                                                            .sorted(comparing(AssuntoDTO::getId))
                                                            .collect(toList());
        final List<Long> assuntosIdsList = assuntosIds.stream()
                                                      .sorted()
                                                      .collect(toList());
        
        IntStream.range(0, assuntosOrdered.size())
                 .forEach(index -> {
                     final AssuntoDTO assunto = assuntosOrdered.get(index);
                     assertNull(assunto.getDescricao());
                     assertNull(assunto.getPeso());
                     assertSame(assuntosIdsList.get(index), assunto.getId());
                 });
    }
}
