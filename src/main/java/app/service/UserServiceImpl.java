package app.service;

import app.dto.user.UserRegistrationRequestDto;
import app.dto.user.UserResponseDto;
import app.exception.RegistrationException;
import app.mapper.UserMapper;
import app.model.User;
import app.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto) {
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User already exists");
        }
        if (!userRegistrationRequestDto.getRepeatPassword()
                .equals(userRegistrationRequestDto.getPassword())) {
            throw new RegistrationException("Passwords do not match");
        }

        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }
}
