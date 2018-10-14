package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.Authority;
import com.rcsoyer.servicosjuridicos.domain.User;
import com.rcsoyer.servicosjuridicos.service.dto.UserDTO;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 * <p>
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {
    
    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }
    
    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
                    .filter(Objects::nonNull)
                    .map(this::userToUserDTO)
                    .collect(toList());
    }
    
    public User userDTOToUser(UserDTO userDTO) {
        Function<UserDTO, User> setUser = dto -> {
            User user = new User()
                            .setId(userDTO.getId())
                            .setLogin(userDTO.getLogin())
                            .setFirstName(userDTO.getFirstName())
                            .setLastName(userDTO.getLastName())
                            .setEmail(userDTO.getEmail())
                            .setImageUrl(userDTO.getImageUrl())
                            .setLangKey(userDTO.getLangKey());
            user.setActivated(userDTO.isActivated());
            Set<Authority> authorities = authoritiesFromStrings(userDTO.getAuthorities());
            Optional.ofNullable(authorities)
                    .ifPresent(user::setAuthorities);
            return user;
        };
        return Optional.ofNullable(userDTO)
                       .map(setUser)
                       .orElse(null);
    }
    
    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
                       .filter(Objects::nonNull)
                       .map(this::userDTOToUser)
                       .collect(toList());
    }
    
    public User userFromId(Long id) {
        return  Optional.ofNullable(id)
                        .map(new User()::setId)
                        .orElse(null);
    }
    
    public Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream()
                      .map(new Authority()::setName)
                      .collect(toSet());
    }
}
