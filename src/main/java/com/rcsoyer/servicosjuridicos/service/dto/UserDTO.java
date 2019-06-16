package com.rcsoyer.servicosjuridicos.service.dto;

import com.rcsoyer.servicosjuridicos.config.Constants;
import com.rcsoyer.servicosjuridicos.domain.Authority;
import com.rcsoyer.servicosjuridicos.domain.User;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO representing a user, with his authorities.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO {
    
    @Min(1L)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
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
    
    private boolean activated = false;
    
    @Size(min = 2, max = 6)
    private String langKey;
    
    private String createdBy;
    
    private Instant createdDate;
    
    private String lastModifiedBy;
    
    private Instant lastModifiedDate;
    
    private Set<String> authorities;
    
    public UserDTO(final User user) {
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
        this.authorities = user.getAuthorities()
                               .stream()
                               .map(Authority::getName)
                               .collect(Collectors.toSet());
    }
    
}
