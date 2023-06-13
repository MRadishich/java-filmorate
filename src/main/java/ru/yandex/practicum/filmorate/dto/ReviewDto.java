package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReviewDto {
    private final Long reviewId;
    @NotBlank(message = "Отзыв не может быть пустым и состоять только из пробелов.")
    private final String content;
    @JsonProperty(value = "isPositive")
    @NotNull(message = "Тип отзыва - обязательное поле.")
    private final Boolean isPositive;
    @NotNull(message = "Идентификатор пользователя = обязательное поле.")
    private final Long userId;
    @NotNull(message = "Идентификатор фильма = обязательное поле.")
    private final Long filmId;
    private final Integer useful;
}
