package app.security;

import app.dto.user.UserLoginRequestDto;
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
        Optional<User> user = userRepository.findByEmail(userLoginRequestDto.getEmail());
        if (user.isPresent() && user.get().getPassword()
                .equals(userLoginRequestDto.getPassword())) {
            return true;
        }
        return false;
    }
}
