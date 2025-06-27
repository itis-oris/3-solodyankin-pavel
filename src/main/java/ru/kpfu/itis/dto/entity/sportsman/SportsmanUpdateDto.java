package ru.kpfu.itis.dto.entity.sportsman;

import lombok.*;
import jakarta.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SportsmanUpdateDto {

    @NotBlank(message = "Имя обязательно")
    @Size(min = 1, max = 50, message = "Имя не больше 50 символов")
    private String name;

    @Min(value = 4, message = "Возраст должен быть больше 4 лет")
    @NotNull(message = "Возраст обязателен")
    private Integer age;

    @NotBlank(message = "Телефон обязателен")
    @Pattern(regexp = "^(\\+7|8)\\d{10}$", message = "Телефон должен быть в формате +79123456789 или 89123456789")
    private String phone;

    @NotBlank(message = "Адрес обязателен")
    @Pattern(regexp = "^г\\.\\s[А-Я][а-яёЁ]+(?:\\s[А-Яа-яёЁ\\-]+)*(?:,\\s*ул\\.\\s[А-Я][а-яёЁ]+(?:\\s[А-Яа-яёЁ\\-]+)*)?(?:,\\s*д\\.\\s*[1-9]\\d*(?:\\s*[А-Яа-яёЁ]|\\s*[-\\/]\\s*[1-9]\\d*)?)?(?:,\\s*кв\\.\\s*[1-9]\\d*[а-яёЁ]?\\.?)?$",
            message = "Адрес должен быть в формате: г. Казань, ул. Ленина, д. 5, кв. 3(со всеми точками и пробелами)")
    private String address;

    @Size(min = 1, max = 50, message = "Звание не больше 50 символов")
    private String rank;
}