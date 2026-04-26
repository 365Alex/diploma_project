package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO, представляющее расширенную информацию об объявлении.
 */
@Data
@Schema(description = "Расширенная реклама")
public class ExtendedAd {
    @Schema(description = "id рекламы")
    private Integer pk;
    @Schema(description = "имя автора")
    private String authorFirstName;
    @Schema(description = "фамилия автора")
    private String authorLastName;
    @Schema(description = "описание рекламы")
    private String description;
    @Schema(description = "email автора")
    private String email;
    @Schema(description = "фото автора ля рекламы")
    private String image;
    @Schema(description = "телефон автора для рекламы")
    private String phone;
    @Schema(description = "цена объявления в рекламе")
    private Integer price;
    @Schema(description = "название рекламы")
    private String title;
}
