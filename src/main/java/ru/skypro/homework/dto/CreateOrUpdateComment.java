package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO для создания или обновления комментария.
 */
@Data
@Schema(description = "Создание или изменение комментария")
public class CreateOrUpdateComment {
    @Schema(description = "текст комментария")
    private String text;
}
