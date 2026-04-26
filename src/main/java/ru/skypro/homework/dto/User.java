package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Добавление нового пользователя")
public class User {
    @Schema(description = "id пользователя")
    private Integer id;
    @Schema(description = "email пользователя")
    private String email;
    @Schema(description = "имя пользователя")
    private String firstName;
    @Schema(description = "фамилия пользователя")
    private String lastName;
    @Schema(description = "телефон пользователя")
    private String phone;
    @Schema(description = "роль пользователя")
    private Role role;
    @Schema(description = "фото пользователя")
    private String image;
}
