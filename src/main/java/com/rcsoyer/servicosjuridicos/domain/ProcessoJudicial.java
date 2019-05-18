package com.rcsoyer.servicosjuridicos.domain;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProcessoJudicial.
 */
@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "numero"})
@Table(name = "processo_judicial")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessoJudicial implements Serializable {
    
    private static final long serialVersionUID = 5978891789222560660L;
    
    @Id
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotBlank
    @Size(min = 20, max = 20)
    @Setter(AccessLevel.NONE)
    @Column(length = 20, nullable = false, unique = true)
    private String numero;
    
    @NotNull
    @Column(name = "prazo_final", nullable = false)
    private LocalDate prazoFinal;
    
    @NotNull
    @Column(name = "dt_atribuicao", nullable = false)
    private LocalDate dtAtribuicao;
    
    @Column(name = "dt_inicio")
    private LocalDate dtInicio;
    
    @Column(name = "dt_conclusao")
    private LocalDate dtConclusao;
    
    @NotNull
    @ManyToOne(optional = false)
    private Assunto assunto;
    
    @NotNull
    @ManyToOne(optional = false)
    private Modalidade modalidade;
    
    @NotNull
    @ManyToOne(optional = false)
    private Advogado advogado;
    
    public ProcessoJudicial setDtAtribuicao(final LocalDate dtAtribuicao) {
        this.dtAtribuicao = Optional.ofNullable(dtAtribuicao)
                                    .orElseGet(LocalDate::now);
        return this;
    }
    
    public ProcessoJudicial setNumero(String numero) {
        this.numero = trimToNull(numero);
        return this;
    }
    
    public boolean foiDistribuido() {
        return Objects.nonNull(id);
    }
    
    public boolean naoFoiAtribuidoAdvogado() {
        return Objects.isNull(advogado);
    }
    
    public void verificarTrocouAdvogado(final ProcessoJudicial consultadoBD) {
        Optional.of(this)
                .filter(advogadoDiferente(consultadoBD))
                .ifPresent(setNewDtAtribuicao());
    }
    
    private Predicate<ProcessoJudicial> advogadoDiferente(final ProcessoJudicial consultadoBD) {
        return processo -> !consultadoBD.getAdvogado().equals(processo.getAdvogado());
    }
    
    private Consumer<ProcessoJudicial> setNewDtAtribuicao() {
        return processo -> processo.setDtAtribuicao(LocalDate.now());
    }
}
