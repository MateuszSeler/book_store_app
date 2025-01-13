package app.security;

import app.dto.user.UserLoginRequestDto;
import app.exception.AuthenticationException;
import app.model.User;
import app.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    @Override
    public boolean authenticate(UserLoginRequestDto userLoginRequestDto) {
        Optional<User> optionalUserByEmail =
                userRepository.findByEmail(userLoginRequestDto.getEmail());

        if (optionalUserByEmail.isEmpty()) {
            throw new AuthenticationException("no user with email:"
                    + userLoginRequestDto.getEmail());
        }
        return true;
    }
}
