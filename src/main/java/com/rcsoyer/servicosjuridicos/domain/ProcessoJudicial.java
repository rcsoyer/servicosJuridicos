package com.rcsoyer.servicosjuridicos.domain;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProcessoJudicial.
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "processo_judicial")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public final class ProcessoJudicial implements Serializable {
    
    private static final long serialVersionUID = 5978891789222560660L;
    
    @Id
    @Min(1L)
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotBlank
    @Size(min = 20, max = 20)
    @Setter(AccessLevel.NONE)
    @Column(length = 20, nullable = false, unique = true)
    private String numero;
    
    @Column(name = "prazo_final", nullable = false)
    private LocalDateTime prazoFinal;
    
    @NotNull
    @Column(name = "dt_atribuicao", nullable = false)
    private LocalDateTime dtAtribuicao;
    
    @NotNull
    @Column(name = "dt_inicio")
    private LocalDateTime dtInicio;
    
    @Column(name = "dt_conclusao")
    private LocalDateTime dtConclusao;
    
    @NotNull
    @ManyToOne(optional = false)
    private Assunto assunto;
    
    @NotNull
    @ManyToOne(optional = false)
    private Modalidade modalidade;
    
    @NotNull
    @ManyToOne(optional = false)
    private Advogado advogado;
    
    public ProcessoJudicial setNumero(String numero) {
        this.numero = trimToNull(numero);
        return this;
    }
    
    /**
     * Get the digit in a process that defines to which attorney the process goes
     */
    public int getSextoDigito() {
        return Integer.parseInt(numero.substring(5));
    }
    
}
