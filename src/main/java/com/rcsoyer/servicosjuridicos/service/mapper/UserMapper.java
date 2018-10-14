package com.rcsoyer.servicosjuridicos.service.mapper;

import com.rcsoyer.servicosjuridicos.domain.User;
import com.rcsoyer.servicosjuridicos.service.dto.UserDTO;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 * <p>
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Component
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
        Function<UserDTO, User> setUser = dto ->
                                              new User()
                                                  .setId(dto.getId())
                                                  .setLogin(dto.getLogin())
                                                  .setFirstName(dto.getFirstName())
                                                  .setLastName(dto.getLastName())
                                                  .setEmail(dto.getEmail())
                                                  .setImageUrl(dto.getImageUrl())
                                                  .setLangKey(dto.getLangKey())
                                                  .setAuthoritiesFrom(dto.getAuthorities())
                                                  .setActivated(dto.isActivated());
        ;
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
}
