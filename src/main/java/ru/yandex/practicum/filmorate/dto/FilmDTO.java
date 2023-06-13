package ru.yandex.practicum.filmorate.dto;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.validation.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
public class FilmDTO {
    public static final int DESCRIPTION_MAX_LENGTH = 200;
    public static final String MIN_DATE_RELEASE = "1895-12-28";

    private final Long id;

    @NotBlank(message = "Название фильма не может быть пустым.")
    private final String name;

    @ReleaseDate(message = "Дата релиза не может быть раньше " + MIN_DATE_RELEASE + ".")
    private final LocalDate releaseDate;

    @Size(max = DESCRIPTION_MAX_LENGTH, message = "Максимальная длина описания — " +
            DESCRIPTION_MAX_LENGTH + " символов.")
    private final String description;

    @Positive(message = "Продолжительность фильма не может быть меньше 0.")
    @NotNull(message = "Продолжительность фильма - обязательное поле.")
    private final Integer duration;

    @NotNull(message = "Рейтинг Ассоциации кинокомпаний - обязательное поле.")
    private final Mpa mpa;

    private final List<Genre> genres;
}
