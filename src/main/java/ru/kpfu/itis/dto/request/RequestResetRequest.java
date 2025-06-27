package ru.kpfu.itis.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestResetRequest {
         @NotBlank(message = "Email обязательно")
         @Email(message = "Некорректный email")
         private String email;
}
