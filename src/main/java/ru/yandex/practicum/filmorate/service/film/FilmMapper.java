package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.film.Film;

class FilmMapper {
    public static FilmDTO toDto(Film film) {
        return new FilmDTO(
                film.getId(),
                film.getName(),
                film.getReleaseDate(),
                film.getDescription(),
                film.getDuration(),
                film.getMpa(),
                film.getGenres()
        );
    }

    public static Film toFilm(FilmDTO filmDTO) {
        return new Film().toBuilder()
                .id(filmDTO.getId())
                .name(filmDTO.getName())
                .description(filmDTO.getDescription())
                .releaseDate(filmDTO.getReleaseDate())
                .duration(filmDTO.getDuration())
                .mpa(filmDTO.getMpa())
                .genres(filmDTO.getGenres())
                .build();
    }
}
