package com.rcsoyer.servicosjuridicos.domain;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.br.CPF;

/**
 * A Advogado
 */
@Entity
@Table(name = "advogado")
@EqualsAndHashCode(of = "id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@ToString(exclude = {"feriasLicencas", "dgCoordenacoes", "processosJudiciais"})
public final class Advogado implements Serializable {
    
    private static final long serialVersionUID = 1619909263889107243L;
    
    @Id
    @Getter
    @Setter
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @Getter
    @NotBlank
    @Size(min = 1, max = 80)
    @Column(length = 80, nullable = false)
    private String nome;
    
    @Getter
    @NotBlank
    @CPF(message = "CPF inválido")
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
    
    @Min(1)
    @Column
    @Getter
    @Setter
    private Integer ramal;
    
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "advogado", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final Set<FeriasLicenca> feriasLicencas;
    
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "advogado", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final Set<AdvogadoDgCoordenacao> dgCoordenacoes;
    
    @OneToMany(mappedBy = "advogado")
    private Set<ProcessoJudicial> processosJudiciais;
    
    public Advogado() {
        this.dgCoordenacoes = new HashSet<>(0);
        this.feriasLicencas = new HashSet<>(0);
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
     * Unmodifiable view of these feriasLicencas
     */
    public Set<FeriasLicenca> getFeriasLicencas() {
        return Set.copyOf(feriasLicencas);
    }
    
    /**
     * Unmodifiable view of these dgCoordenacoes
     */
    public Set<AdvogadoDgCoordenacao> getDgCoordenacoes() {
        return Set.copyOf(dgCoordenacoes);
    }
    
    public Advogado addFeriasLicenca(final FeriasLicenca feriasLicenca) {
        feriasLicencas.add(feriasLicenca);
        feriasLicenca.setAdvogado(this);
        return this;
    }
    
    public Advogado removeFeriasLicenca(final FeriasLicenca feriasLicenca) {
        feriasLicencas.remove(feriasLicenca);
        feriasLicenca.setAdvogado(null);
        return this;
    }
    
    public Advogado addDgCoordenacao(final AdvogadoDgCoordenacao advogadoDgCoordenacao) {
        dgCoordenacoes.add(advogadoDgCoordenacao);
        advogadoDgCoordenacao.setAdvogado(this);
        return this;
    }
    
    public Advogado removeDgCoordenacao(final AdvogadoDgCoordenacao advogadoDgCoordenacao) {
        dgCoordenacoes.remove(advogadoDgCoordenacao);
        advogadoDgCoordenacao.setAdvogado(null);
        return this;
    }
    
    public int numberOfProcessosJudiciais() {
        return processosJudiciais.size();
    }
    
    /**
     * Business rule: an advocate cannot receive processes if it's 5 days for it's vacations(feriasLicenca)
     */
    boolean canReceiveProcesso() {
        if (isNotEmpty(feriasLicencas)) {
            Predicate<FeriasLicenca> feriasLicencaInLessThanFiveDays = FeriasLicenca::feriasLicencaInLessThanFiveDays;
            Predicate<FeriasLicenca> feriasLicencaInProgress = FeriasLicenca::feriasLicencaInProgress;
            return feriasLicencas.stream()
                                 .noneMatch(feriasLicencaInLessThanFiveDays
                                                .or(feriasLicencaInProgress));
        }
        
        return true;
    }
    
}
