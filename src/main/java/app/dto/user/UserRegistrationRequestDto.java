package app.dto.user;

import app.validator.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRegistrationRequestDto {
    @Email
    private String email;
    @Length(min = 6, max = 20)
    private String password;
    @Length(min = 6, max = 20)
    private String repeatPassword;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String shippingAddress;
}
