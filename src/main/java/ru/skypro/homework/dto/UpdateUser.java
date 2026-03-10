package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "обновить данные пользователя")
public class UpdateUser {
    @Schema(description = "изменить имя")
    private String firstName;
    @Schema(description = "изменить фамилию")
    private String lastName;
    @Schema(description = "изменить телефон")
    private String phone;
}
