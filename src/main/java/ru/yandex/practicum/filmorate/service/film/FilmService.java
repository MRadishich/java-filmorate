package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.dto.FilmDTO;

import java.util.Collection;

public interface FilmService {
    FilmDTO createFilm(FilmDTO film);

    Collection<FilmDTO> findAllFilms();

    FilmDTO findFilmById(Long id);

    FilmDTO updateFilm(FilmDTO filmDTO);

    void deleteFilmById(Long id);

    Collection<FilmDTO> findPopularFilms(int count);
}
