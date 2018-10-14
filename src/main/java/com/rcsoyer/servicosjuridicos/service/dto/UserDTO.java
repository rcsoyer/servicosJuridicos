package com.rcsoyer.servicosjuridicos.service.dto;

import com.rcsoyer.servicosjuridicos.config.Constants;
import com.rcsoyer.servicosjuridicos.domain.User;
import java.time.Instant;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A DTO representing a user, with his authorities.
 */
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@ToString(exclude = "authorities")
public class UserDTO {
    
    private Long id;
    
    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = Constants.LOGIN_REGEX)
    private String login;
    
    @Size(max = 50)
    private String firstName;
    
    @Size(max = 50)
    private String lastName;
    
    @Email
    @Size(min = 5, max = 254)
    private String email;
    
    @Size(max = 256)
    private String imageUrl;
    
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean activated = false;
    
    @Size(min = 2, max = 6)
    private String langKey;
    
    private String createdBy;
    
    private Instant createdDate;
    
    private String lastModifiedBy;
    
    private Instant lastModifiedDate;
    
    private Set<String> authorities;
    
    public UserDTO() {
    }
    
    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.getActivated();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.authorities = user.getAuthoritiesNames();
    }
    
    public boolean isActivated() {
        return activated;
    }
    
    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
