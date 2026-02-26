package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "регистрация")
public class Login {
    @Schema(description = "Имя пользователя")
    private String username;
    @Schema(description = "Пароль пользователя")
    private String password;
}
