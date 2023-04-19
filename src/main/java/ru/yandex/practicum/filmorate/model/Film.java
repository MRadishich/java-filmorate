package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validation.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    public static final int DESCRIPTION_MAX_LENGTH = 200;
    public static final String MIN_DATE_RELEASE = "1895-12-28";

    private Long id;

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

    private final Set<Long> likes = new HashSet<>();

    public void addLike(long userId) {
        likes.add(userId);
    }

    public void deleteLike(long userId) {
        likes.remove(userId);
    }
}
