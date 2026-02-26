package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "изменить пароль")
public class NewPassword {
    @Schema(description = "старый пароль")
    private String currentPassword;
    @Schema(description = "новый пароль")
    private String newPassword;

}
