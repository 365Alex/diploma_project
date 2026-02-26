package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Все комментарии")
public class Comments {
    @Schema(description = "Количество комментариев")
    private Integer count;
    @Schema(description = "Список всех комментариев")
    private List<Comment> results;
}
