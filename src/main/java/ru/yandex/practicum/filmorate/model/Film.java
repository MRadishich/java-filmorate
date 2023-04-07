package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.yandex.practicum.filmorate.validation.ReleaseDate;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    public static final int DESCRIPTION_MAX_LENGTH = 200;
    public static final String MIN_DATE_RELEASE = "1895-12-28";

    @EqualsAndHashCode.Exclude
    private int id;

    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;

    @Size(max = DESCRIPTION_MAX_LENGTH, message = "Максимальная длина описания — " +
            DESCRIPTION_MAX_LENGTH + " символов.")
    private String description;

    @ReleaseDate(message = "Дата релиза не может быть раньше " + MIN_DATE_RELEASE + ".")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма не может быть меньше 0.")
    @NotNull(message = "Продолжительность фильма - обязательное поле.")
    private Integer duration;
}
