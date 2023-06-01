package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.dto.FilmDTO;

import java.util.List;

public interface FilmService {
    FilmDTO createFilm(FilmDTO film);

    List<FilmDTO> findAllFilms();

    FilmDTO findFilmById(Long id);

    FilmDTO updateFilm(FilmDTO filmDTO);

    void deleteFilmById(Long id);

    List<FilmDTO> findPopularFilms(int count);
}
