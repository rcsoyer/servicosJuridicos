package com.rcsoyer.servicosjuridicos.domain;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.br.CPF;

/**
 * A Advogado.
 */
@Entity
@Getter
@Accessors(chain = true)
@Table(name = "advogado")
@EqualsAndHashCode(of = {"id", "cpf"})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@ToString(exclude = {"processos", "feriasLicencas", "dgCoordenacoes"})
public final class Advogado implements Serializable {
    
    private static final long serialVersionUID = 1619909263889107243L;
    
    @Id
    @Setter
    @Column(updatable = false, nullable = false)
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotBlank
    @Size(max = 80)
    @Column(length = 80, nullable = false)
    private String nome;
    
    @CPF
    @NotBlank
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
    
    @Column
    @Setter
    private Integer ramal;
    
    @JsonIgnore
    @OneToMany(mappedBy = "advogado", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProcessoJudicial> processos = new HashSet<>(0);
    
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "advogado", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<FeriasLicenca> feriasLicencas = new HashSet<>(0);
    
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "advogado", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<AdvogadoDgCoordenacao> dgCoordenacoes = new HashSet<>(0);
    
    public Advogado setProcessos(final Set<ProcessoJudicial> processos) {
        this.processos = Optional.ofNullable(processos)
                                 .map(HashSet::new)
                                 .orElseGet(() -> new HashSet<>(0));
        return this;
    }
    
    public Advogado setFeriasLicencas(final Set<FeriasLicenca> feriasLicencas) {
        this.feriasLicencas = Optional.ofNullable(feriasLicencas)
                                      .map(HashSet::new)
                                      .orElseGet(() -> new HashSet<>(0));
        return this;
    }
    
    public Advogado setDgCoordenacoes(final Set<AdvogadoDgCoordenacao> dgCoordenacoes) {
        this.dgCoordenacoes = Optional.ofNullable(dgCoordenacoes)
                                      .map(HashSet::new)
                                      .orElseGet(() -> new HashSet<>(0));
        return this;
    }
    
    public Advogado setNome(String nome) {
        this.nome = trimToNull(nome);
        return this;
    }
    
    public Advogado setCpf(String cpf) {
        this.cpf = trimToNull(cpf);
        return this;
    }
    
    /**
     * Unmodifiable view of these processos
     */
    public Set<ProcessoJudicial> getProcessos() {
        return ImmutableSet.copyOf(processos);
    }
    
    /**
     * Unmodifiable view of these feriasLicencas
     */
    public Set<FeriasLicenca> getFeriasLicencas() {
        return ImmutableSet.copyOf(feriasLicencas);
    }
    
    /**
     * Unmodifiable view of these dgCoordenacoes
     */
    public Set<AdvogadoDgCoordenacao> getDgCoordenacoes() {
        return ImmutableSet.copyOf(dgCoordenacoes);
    }
    
    public Advogado addProcesso(ProcessoJudicial processoJudicial) {
        processos.add(processoJudicial);
        processoJudicial.setAdvogado(this);
        return this;
    }
    
    public Advogado removeProcesso(ProcessoJudicial processoJudicial) {
        processos.remove(processoJudicial);
        processoJudicial.setAdvogado(null);
        return this;
    }
    
    public Advogado addFeriasLicenca(FeriasLicenca feriasLicenca) {
        feriasLicencas.add(feriasLicenca);
        feriasLicenca.setAdvogado(this);
        return this;
    }
    
    public Advogado removeFeriasLicenca(FeriasLicenca feriasLicenca) {
        feriasLicencas.remove(feriasLicenca);
        feriasLicenca.setAdvogado(null);
        return this;
    }
    
    public Advogado addDgCoordenacao(AdvogadoDgCoordenacao advogadoDgCoordenacao) {
        dgCoordenacoes.add(advogadoDgCoordenacao);
        advogadoDgCoordenacao.setAdvogado(this);
        return this;
    }
    
    public Advogado removeDgCoordenacao(AdvogadoDgCoordenacao advogadoDgCoordenacao) {
        dgCoordenacoes.remove(advogadoDgCoordenacao);
        advogadoDgCoordenacao.setAdvogado(null);
        return this;
    }
}
