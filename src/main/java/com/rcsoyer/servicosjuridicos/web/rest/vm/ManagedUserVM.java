package com.rcsoyer.servicosjuridicos.web.rest.vm;

import com.rcsoyer.servicosjuridicos.service.dto.UserDTO;
import java.time.Instant;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, exclude = "password")
public final class ManagedUserVM extends UserDTO {
    
    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;
    
    @Setter
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
    
    @Override
    public ManagedUserVM setId(Long id) {
        super.setId(id);
        return this;
    }
    
    @Override
    public ManagedUserVM setLogin(String login) {
        super.setLogin(login);
        return this;
    }
    
    @Override
    public ManagedUserVM setFirstName(String firstName) {
        super.setFirstName(firstName);
        return this;
    }
    
    @Override
    public ManagedUserVM setLastName(String lastName) {
        super.setLastName(lastName);
        return this;
    }
    
    @Override
    public ManagedUserVM setEmail(String email) {
        super.setEmail(email);
        return this;
    }
    
    @Override
    public ManagedUserVM setImageUrl(String imageUrl) {
        super.setImageUrl(imageUrl);
        return this;
    }
    
    @Override
    public ManagedUserVM setLangKey(String langKey) {
        super.setLangKey(langKey);
        return this;
    }
    
    @Override
    public ManagedUserVM setActivated(boolean activated) {
        super.setActivated(activated);
        return this;
    }
    
    @Override
    public ManagedUserVM setCreatedBy(String createdBy) {
        super.setCreatedBy(createdBy);
        return this;
    }
    
    @Override
    public ManagedUserVM setCreatedDate(Instant createdDate) {
        super.setCreatedDate(createdDate);
        return this;
    }
    
    @Override
    public ManagedUserVM setLastModifiedBy(String lastModifiedBy) {
        super.setLastModifiedBy(lastModifiedBy);
        return this;
    }
    
    @Override
    public ManagedUserVM setLastModifiedDate(Instant lastModifiedDate) {
        super.setLastModifiedDate(lastModifiedDate);
        return this;
    }
    
    @Override
    public ManagedUserVM setAuthorities(Set<String> authorities) {
        super.setAuthorities(authorities);
        return this;
    }
    
}
