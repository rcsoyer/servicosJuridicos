package com.rcsoyer.servicosjuridicos.service.mapper;

import static java.util.stream.Collectors.toSet;

import com.rcsoyer.servicosjuridicos.domain.Authority;
import com.rcsoyer.servicosjuridicos.domain.User;
import com.rcsoyer.servicosjuridicos.service.dto.UserDTO;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct support is still in beta, and
 * requires a manual step with an IDE.
 */
@Component
public class UserMapper {
    
    public UserDTO userToUserDTO(final User user) {
        return new UserDTO()
                   .setId(user.getId())
                   .setLogin(user.getLogin())
                   .setFirstName(user.getFirstName())
                   .setLastName(user.getLastName())
                   .setEmail(user.getEmail())
                   .setActivated(user.getActivated())
                   .setImageUrl(user.getImageUrl())
                   .setLangKey(user.getLangKey())
                   .setCreatedBy(user.getCreatedBy())
                   .setCreatedDate(user.getCreatedDate())
                   .setLastModifiedBy(user.getLastModifiedBy())
                   .setLastModifiedDate(user.getLastModifiedDate())
                   .setAuthorities(user.getAuthorities()
                                       .stream()
                                       .map(Authority::getName)
                                       .collect(toSet()));
    }
    
    public User userDTOToUser(final UserDTO userDTO) {
        return Optional.ofNullable(userDTO)
                       .map(dto -> {
                           final User user = new User();
                           user.setId(dto.getId());
                           user.setLogin(dto.getLogin());
                           user.setFirstName(dto.getFirstName());
                           user.setLastName(dto.getLastName());
                           user.setEmail(dto.getEmail());
                           user.setImageUrl(dto.getImageUrl());
                           user.setActivated(dto.isActivated());
                           user.setLangKey(dto.getLangKey());
            
                           Set<Authority> authorities = authoritiesFromStrings(dto.getAuthorities());
                           Optional.ofNullable(authorities)
                                   .ifPresent(user::setAuthorities);
                           return user;
                       })
                       .orElse(null);
    }
    
    public User userFromId(Long id) {
        return Optional.ofNullable(id)
                       .map(new User()::setId)
                       .orElse(null);
    }
    
    private Set<Authority> authoritiesFromStrings(Set<String> authorities) {
        return authorities.stream()
                          .map(authorityName -> {
                              final Authority auth = new Authority();
                              auth.setName(authorityName);
                              return auth;
                          }).collect(toSet());
    }
}
