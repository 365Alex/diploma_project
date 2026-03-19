package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO для создания или обновления объявления.
 */
@Data
@Schema(description = "Создать или изменить объявление")
public class CreateOrUpdateAd {
    @Schema(description = "Название объявления")
    private String title;
    @Schema(description = "Цена объявления")
    private Integer price;
    @Schema(description = "В описание объявления")
    private String description;
}
