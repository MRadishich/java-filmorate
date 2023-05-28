package ru.yandex.practicum.filmorate.model.film;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;

    @NotNull
    private String name;

    private String description;

    private LocalDate releaseDate;

    @NotNull
    private Integer duration;

    private Integer mpaId;

    private List<Genre> genres;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id) && Objects.equals(name, film.name) && Objects.equals(description, film.description) && Objects.equals(releaseDate, film.releaseDate) && Objects.equals(duration, film.duration) && Objects.equals(mpaId, film.mpaId) && Objects.equals(genres, film.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, releaseDate, duration, mpaId, genres);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", mpaId=" + mpaId +
                ", genres=" + genres +
                '}';
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("title", name);
        map.put("description", description);
        map.put("release_date", releaseDate);
        map.put("duration", duration);
        map.put("mpa_rating_id", mpaId);

        return map;
    }
}

