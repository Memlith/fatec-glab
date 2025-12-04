package com.fatec.glab.mapper;

import com.fatec.glab.dto.user.UserResponseDTO;
import com.fatec.glab.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toUserResponseDTO(User user);
}
