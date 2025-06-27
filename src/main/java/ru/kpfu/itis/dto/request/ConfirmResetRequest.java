package ru.kpfu.itis.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmResetRequest{
        @NotBlank(message = "Email обязательно")
        @Email(message = "Некорректный email")
        private String email;
        @NotBlank
        @Size(min = 1, max = 10)
        private String code;
        @NotBlank(message = "Пароль обязательно")
        @Size(min = 1, max = 100, message = "Пароль не больше 100 символов")
        private String newPassword;
}
