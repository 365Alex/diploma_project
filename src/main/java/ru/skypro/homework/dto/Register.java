package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Данные для регистрации")
public class Register {

    @NotBlank
    @Size(min = 4, max = 32)
    @Schema(description = "Логин пользователя", minLength = 4, maxLength = 32, example = "user@example.com")
    private String username;

    @NotBlank
    @Size(min = 8, max = 16)
    @Schema(description = "Пароль пользователя", minLength = 8, maxLength = 16, example = "password123")
    private String password;

    @NotBlank
    @Size(min = 2, max = 16)
    @Schema(description = "Имя пользователя", minLength = 2, maxLength = 16, example = "Иван")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 16)
    @Schema(description = "Фамилия пользователя", minLength = 2, maxLength = 16, example = "Иванов")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    @Schema(description = "Телефон пользователя", pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", example = "+7 (123) 456-78-90")
    private String phone;

    @NotNull
    @Schema(description = "Роль пользователя", allowableValues = {"USER", "ADMIN"}, example = "USER")
    private Role role;
}
