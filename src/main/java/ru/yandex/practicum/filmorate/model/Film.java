package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.FilmeDuration;
import ru.yandex.practicum.filmorate.validation.ReleaseDate;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private static final int DESCRIPTION_MAX_LENGTH = 200;
    private int id;
    @NotBlank(message = "Название фильма не может быть пустым.")
    private final String name;
    @Size(max = DESCRIPTION_MAX_LENGTH, message = "Максимальная длина описания — 200 символов.")
    private final String description;
    @ReleaseDate(message = "Дата релиза не может быть раньше 1985-12-28.")
    private final LocalDate releaseDate;
    @FilmeDuration(message = "Продолжительность фильма - обязательное поле. " +
            "Продолжительность фильма не может быть меньше 0.")
    private final Duration duration;
}
