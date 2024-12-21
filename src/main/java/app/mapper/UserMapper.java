package app.mapper;

import app.config.MapperConfig;
import app.dto.user.UserRegistrationRequestDto;
import app.dto.user.UserResponseDto;
import app.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User book);

    User toModel(UserRegistrationRequestDto registrationRequestDto);
}
