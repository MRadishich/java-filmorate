package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

class FilmMapper {
    public static FilmDto toDto(Film film) {
        return new FilmDto(
                film.getId(),
                film.getName(),
                film.getReleaseDate(),
                film.getDescription(),
                film.getDuration(),
                film.getMpa(),
                film.getGenres()
        );
    }

    public static Film toFilm(FilmDto filmDTO) {
        return new Film().toBuilder()
                .id(filmDTO.getId())
                .name(filmDTO.getName())
                .description(filmDTO.getDescription())
                .releaseDate(filmDTO.getReleaseDate())
                .duration(filmDTO.getDuration())
                .mpa(filmDTO.getMpa())
                .genres(filmDTO.getGenres() == null ? List.of() : filmDTO.getGenres())
                .build();
    }
}
