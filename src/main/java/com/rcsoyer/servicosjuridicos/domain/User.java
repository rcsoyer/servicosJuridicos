package com.rcsoyer.servicosjuridicos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rcsoyer.servicosjuridicos.config.Constants;
import com.rcsoyer.servicosjuridicos.service.util.RandomUtil;
import java.io.Serializable;
import static java.lang.Boolean.FALSE;
import java.time.Instant;
import java.util.HashSet;
import static java.util.Optional.ofNullable;
import java.util.Set;
import java.util.function.Consumer;
import static java.util.stream.Collectors.toSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A user.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "jhi_user")
@ToString(exclude = "authorities")
@EqualsAndHashCode(of = "id", callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {
    
    private static final long serialVersionUID = -3094760790060400981L;
    
    @Id
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotNull
    @Size(min = 1, max = 50)
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Column(length = 50, unique = true, nullable = false)
    private String login;
    
    @NotNull
    @JsonIgnore
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;
    
    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;
    
    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;
    
    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;
    
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private Boolean activated;
    
    @Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 6)
    private String langKey;
    
    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;
    
    @JsonIgnore
    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    private String activationKey;
    
    @JsonIgnore
    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;
    
    @Column(name = "reset_date")
    private Instant resetDate;
    
    @JsonIgnore
    @ManyToMany
    @BatchSize(size = 20)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<Authority> authorities = new HashSet<>(0);
    
    public User setActivated(Boolean activated) {
        this.activated = ofNullable(activated).orElse(FALSE);
        return this;
    }
    
    public Set<String> getAuthoritiesNames() {
        return authorities.stream()
                          .map(Authority::getName)
                          .collect(toSet());
    }
    
    public User setAuthoritiesFrom(Set<String> strAuthorities) {
        Consumer<Set<String>> extractAndSet =
            strAuths-> authorities = strAuths.stream()
                                             .map(new Authority()::setName)
                                             .collect(toSet());
        ofNullable(strAuthorities).ifPresent(extractAndSet);
        return this;
    }
    
    public boolean isResetDateUpToDate() {
        return resetDate.isAfter(Instant.now().minusSeconds(86400));
    }
    
    public User removeAuthorities() {
        authorities.clear();
        return this;
    }
    
    public User setLangKey(String langKey) {
        this.langKey = ofNullable(langKey).orElse(Constants.DEFAULT_LANGUAGE);
        return this;
    }
    
    public User setKeyDateResetDefault() {
        return setResetKey(RandomUtil.generateResetKey())
               .setResetDate(Instant.now());
    }
    
    public User setKeyDateResetToNull() {
        return setResetKey(null).setResetDate(null);
    }
    
    public User addAuthorities(Set<Authority> authorities) {
        this.authorities.addAll(authorities);
        return this;
    }
}
