package com.rcsoyer.servicosjuridicos.domain;

import static org.apache.commons.lang3.StringUtils.trimToNull;
import static org.apache.commons.lang3.StringUtils.upperCase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
 * A CoordenacaoJuridica. <p> When creating or updating all the fields are required
 */
@Entity
@Getter
@Accessors(chain = true)
@ToString(exclude = "dgAdvogados")
@Table(name = "coordenacao_juridica")
@EqualsAndHashCode(of = {"id", "sigla", "nome"})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CoordenacaoJuridica implements Serializable {
    
    private static final long serialVersionUID = 7821224258437788453L;
    
    @Id
    @Setter
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotBlank
    @Size(max = 6)
    @Column(length = 6, nullable = false, unique = true)
    private String sigla;
    
    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false, unique = true)
    private String nome;
    
    @NotBlank
    @Column(length = 3)
    @Pattern(regexp = "[\\d]{3}")
    private String centena;
    
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "coordenacao", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final Set<AdvogadoDgCoordenacao> dgAdvogados;
    
    @NotEmpty
    @ManyToMany
    @Getter(AccessLevel.NONE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "coordenacao_juridica_assunto",
        joinColumns = @JoinColumn(name = "coordenacao_juridicas_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "assuntos_id", referencedColumnName = "id"))
    private final Set<Assunto> assuntos;
    
    public CoordenacaoJuridica() {
        this.assuntos = new HashSet<>(0);
        this.dgAdvogados = new HashSet<>(0);
    }
    
    public CoordenacaoJuridica setCentena(String centena) {
        this.centena = trimToNull(centena);
        return this;
    }
    
    public CoordenacaoJuridica setSigla(String sigla) {
        this.sigla = trimToNull(upperCase(sigla));
        return this;
    }
    
    public CoordenacaoJuridica setNome(String nome) {
        this.nome = trimToNull(nome);
        return this;
    }
    
    public CoordenacaoJuridica addDgAdvogado(final AdvogadoDgCoordenacao advogadoDgCoordenacao) {
        advogadoDgCoordenacao.setCoordenacao(this);
        dgAdvogados.add(advogadoDgCoordenacao);
        return this;
    }
    
    public CoordenacaoJuridica removeDgAdvogado(final AdvogadoDgCoordenacao advogadoDgCoordenacao) {
        dgAdvogados.remove(advogadoDgCoordenacao);
        advogadoDgCoordenacao.setCoordenacao(null);
        return this;
    }
    
    public CoordenacaoJuridica addAssunto(final Assunto assunto) {
        assuntos.add(assunto);
        return this;
    }
    
    public CoordenacaoJuridica addAssuntos(final Collection<Assunto> assuntos) {
        this.assuntos.addAll(assuntos);
        return this;
    }
    
    public CoordenacaoJuridica removeAssunto(final Assunto assunto) {
        assuntos.remove(assunto);
        return this;
    }
    
    /**
     * Creates a immutable copy of these assuntos
     */
    public Set<Assunto> getAssuntos() {
        return ImmutableSet.copyOf(assuntos);
    }
    
    /**
     * Creates a immutable copy of these advogadoDgCoordenacao
     */
    public Set<AdvogadoDgCoordenacao> getDgAdvogados() {
        return ImmutableSet.copyOf(dgAdvogados);
    }
}
