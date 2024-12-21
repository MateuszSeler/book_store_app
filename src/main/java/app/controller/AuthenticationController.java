package app.controller;

import app.dto.user.UserLoginRequestDto;
import app.dto.user.UserRegistrationRequestDto;
import app.dto.user.UserResponseDto;
import app.security.AuthenticationService;
import app.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "authentication manger",
        description = "Endpoints for authentication users in book shop app")
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("registration")
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto userRegistrationRequestDto) {
        return userService.register(userRegistrationRequestDto);
    }

    @PostMapping("login")
    public boolean login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        return authenticationService.authenticate(userLoginRequestDto);
    }
}
