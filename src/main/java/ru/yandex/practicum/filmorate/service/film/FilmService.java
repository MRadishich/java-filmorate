package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.dto.FilmDTO;

import java.util.List;

public interface FilmService {
    FilmDTO createFilm(FilmDTO film);

    List<FilmDTO> getAllFilms();

    FilmDTO getFilmById(long id);

    FilmDTO updateFilm(FilmDTO filmDTO);

    void deleteFilmById(long id);

    List<FilmDTO> getPopularFilms(int count);
}
