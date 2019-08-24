package com.rcsoyer.servicosjuridicos.service.dto;

import static com.rcsoyer.servicosjuridicos.config.Constants.LOGIN_REGEX;

import com.rcsoyer.servicosjuridicos.domain.Authority;
import com.rcsoyer.servicosjuridicos.domain.User;
import com.rcsoyer.servicosjuridicos.service.dto.view.UserView;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
@EqualsAndHashCode(of = {"id", "login", "firstName"})
public class UserDTO {
    
    @Null(groups = UserView.Create.class)
    @NotNull(groups = UserView.Update.class)
    @Min(value = 1L, groups = UserView.Update.class)
    private Long id;
    
    @NotBlank(groups = {UserView.Create.class, UserView.Update.class})
    @Size(min = 1, max = 50, groups = {UserView.Create.class, UserView.Update.class})
    @Pattern(regexp = LOGIN_REGEX, groups = {UserView.Create.class, UserView.Update.class})
    private String login;
    
    @NotBlank(groups = {UserView.Create.class, UserView.Update.class})
    @Size(min = 1, max = 50, groups = {UserView.Create.class, UserView.Update.class})
    private String firstName;
    
    @NotBlank(groups = {UserView.Create.class, UserView.Update.class})
    @Size(min = 1, max = 50, groups = {UserView.Create.class, UserView.Update.class})
    private String lastName;
    
    @Email(groups = {UserView.Create.class, UserView.Update.class})
    @Size(min = 5, max = 254, groups = {UserView.Create.class, UserView.Update.class})
    private String email;
    
    @Size(min = 1, max = 256, groups = {UserView.Create.class, UserView.Update.class})
    private String imageUrl;
    
    @Size(min = 2, max = 6, groups = {UserView.Create.class, UserView.Update.class})
    private String langKey;
    
    @Null(groups = {UserView.Create.class})
    @NotNull(groups = UserView.Update.class)
    private String createdBy;
    
    @Null(groups = {UserView.Create.class})
    @NotNull(groups = UserView.Update.class)
    private Instant createdDate;
    
    @NotBlank(groups = UserView.Update.class)
    private String lastModifiedBy;
    
    @NotNull(groups = UserView.Update.class)
    private Instant lastModifiedDate;
    
    @NotEmpty(groups = {UserView.Create.class, UserView.Update.class})
    private Set<String> authorities;
    
    private boolean activated;
    
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
