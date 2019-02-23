package com.rcsoyer.servicosjuridicos.domain;

import static org.apache.commons.lang3.StringUtils.trimToNull;
import static org.apache.commons.lang3.StringUtils.upperCase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
 * A CoordenacaoJuridica.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "coordenacao_juridica")
@ToString(exclude = {"dgAdvogados", "assuntos"})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CoordenacaoJuridica implements Serializable {
    
    private static final long serialVersionUID = 7821224258437788453L;
    
    @Id
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotEmpty
    @Size(min = 1, max = 6)
    @Setter(AccessLevel.NONE)
    @Column(length = 6, nullable = false)
    private String sigla;
    
    @NotEmpty
    @Size(min = 1, max = 50)
    @Setter(AccessLevel.NONE)
    @Column(length = 50, nullable = false)
    private String nome;
    
    @Column(length = 3)
    @Size(min = 3, max = 3)
    @Setter(AccessLevel.NONE)
    private String centena;
    
    @JsonIgnore
    @OneToMany(mappedBy = "coordenacao", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdvogadoDgCoordenacao> dgAdvogados = new HashSet<>(0);
    
    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "coordenacao_juridica_assunto",
        joinColumns = @JoinColumn(name = "coordenacao_juridicas_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "assuntos_id", referencedColumnName = "id"))
    private Set<Assunto> assuntos = new HashSet<>(0);
    
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
    
    public CoordenacaoJuridica addDgAdvogado(AdvogadoDgCoordenacao advogadoDgCoordenacao) {
        dgAdvogados.add(advogadoDgCoordenacao);
        advogadoDgCoordenacao.setCoordenacao(this);
        return this;
    }
    
    public CoordenacaoJuridica removeDgAdvogado(AdvogadoDgCoordenacao advogadoDgCoordenacao) {
        dgAdvogados.remove(advogadoDgCoordenacao);
        advogadoDgCoordenacao.setCoordenacao(null);
        return this;
    }
    
    public CoordenacaoJuridica addAssunto(Assunto assunto) {
        assuntos.add(assunto);
        return this;
    }
    
    public CoordenacaoJuridica removeAssunto(Assunto assunto) {
        assuntos.remove(assunto);
        return this;
    }
}
