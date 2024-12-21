package app.service;

import app.dto.user.UserRegistrationRequestDto;
import app.dto.user.UserResponseDto;
import app.exception.RegistrationException;
import app.mapper.UserMapper;
import app.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto) {
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User already exists");
        }
        if (!userRegistrationRequestDto.getRepeatPassword()
                .equals(userRegistrationRequestDto.getPassword())) {
            throw new RegistrationException("Passwords do not match");
        }
        return userMapper.toDto(userRepository.save(
                userMapper.toModel(userRegistrationRequestDto)));
    }
}
