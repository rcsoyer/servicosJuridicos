package com.rcsoyer.servicosjuridicos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rcsoyer.servicosjuridicos.config.Constants;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
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
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A user.
 */
@Entity
@Setter
@Getter
@Table(name = "jhi_user")
@ToString(exclude = "authorities")
@EqualsAndHashCode(of = "id", callSuper = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public final class User extends AbstractAuditingEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    @NotNull
    @Size(min = 1, max = 50)
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Column(length = 50, unique = true, nullable = false)
    private String login;
    
    @JsonIgnore
    @NotNull
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
    @Column(nullable = false)
    @Getter(AccessLevel.NONE)
    private boolean activated = true;
    
    @Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 6)
    private String langKey;
    
    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;
    
    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;
    
    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;
    
    @Column(name = "reset_date")
    private Instant resetDate = null;
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();
    
    /**
     * Lowercase the login before saving it in database
     */
    public User setLogin(String login) {
        this.login = login.trim()
                          .toLowerCase(Locale.ENGLISH);
        return this;
    }
    
    public User setPassword(String password) {
        this.password = password.trim();
        return this;
    }
    
    public User setFirstName(String firstName) {
        this.firstName = firstName.trim();
        return this;
    }
    
    public User setLastName(String lastName) {
        this.lastName = lastName.trim();
        return this;
    }
    
    public User setEmail(String email) {
        this.email = email.trim();
        return this;
    }
    
    public User setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl.trim();
        return this;
    }
    
    public User setActivationKey(String activationKey) {
        this.activationKey = activationKey.trim();
        return this;
    }
    
    public User setResetKey(String resetKey) {
        this.resetKey = resetKey.trim();
        return this;
    }
    
    public User setLangKey(String langKey) {
        this.langKey = langKey.trim();
        return this;
    }
    
    /**
     * Immutable copy of these user's authorities
     */
    public Set<Authority> getAuthorities() {
        return Set.copyOf(authorities);
    }
    
    public User setAuthorities(final Set<Authority> authorities) {
        this.authorities = new HashSet<>(authorities);
        return this;
    }
    
    public void removeAllAuthorities() {
        authorities.clear();
    }
    
    public void addAuthority(final Authority authority) {
        authorities.add(authority);
    }
    
    public boolean getActivated() {
        return activated;
    }
    
}
