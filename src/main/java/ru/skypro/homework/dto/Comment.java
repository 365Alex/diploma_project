package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Комментарий")
public class Comment {
    @Schema(description = "id автора комментария")
    private Integer author;
    @Schema(description = "аватарка автора комментария")
    private String authorImage;
    @Schema(description = "имя автора комментария")
    private String authorFirstName;
    @Schema(description = "создание комментария")
    private Long createdAt;
    @Schema(description = "id комментария")
    private Integer pk;
    @Schema(description = "текст комментария")
    private String text;
}
