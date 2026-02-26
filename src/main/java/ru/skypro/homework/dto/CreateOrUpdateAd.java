package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Создать или изменить объявление")
public class CreateOrUpdateAd {
    @Schema(description = "Название объявления")
    private String title;
    @Schema(description = "Цена объявления")
    private Integer price;
    @Schema(description = "ВОписание объявления")
    private String description;
}
