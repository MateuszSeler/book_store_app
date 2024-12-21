package app.security;

import app.dto.user.UserLoginRequestDto;

public interface AuthenticationService {
    boolean authenticate(UserLoginRequestDto userLoginRequestDto);
}
