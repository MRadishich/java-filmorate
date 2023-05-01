package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Film create(Film film);

    Collection<Film> findAll();

    Film findById(long id);

    Film update(Film film);

    void deleteById(long id);

    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    Collection<Film> findPopular(long count);
}
