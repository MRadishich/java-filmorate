package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film create(Film film);

    Collection<Film> findAll();

    Film findById(long id);

    Collection<Film> findByIds(Collection<Long> ids);

    Film update(Film film);

    void deleteById(long id);

    boolean existById(long filmId);

}
