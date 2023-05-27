package ru.yandex.practicum.filmorate.service.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.util.function.Function;

@Component
class FilmToDTOMapper implements Function<Film, FilmDTO> {
    private final MpaDbStorage mpaDbStorage;

    public FilmToDTOMapper(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    @Override
    public FilmDTO apply(Film film) {
        return new FilmDTO(
                film.getId(),
                film.getName(),
                film.getReleaseDate(),
                film.getDescription(),
                film.getDuration(),
                mpaDbStorage.findById(film.getMpaId()).orElse(null),
                film.getGenres()
        );
    }
}
