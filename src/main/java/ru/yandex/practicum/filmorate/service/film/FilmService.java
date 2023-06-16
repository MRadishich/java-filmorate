package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.dto.FilmDto;

import java.util.List;

public interface FilmService {
    FilmDto createFilm(FilmDto film);

    List<FilmDto> getAllFilms();

    FilmDto getFilmById(long id);

    FilmDto updateFilm(FilmDto filmDTO);

    void deleteFilmById(long id);

    List<FilmDto> getTopFilmsByLikes(int limit);
}
