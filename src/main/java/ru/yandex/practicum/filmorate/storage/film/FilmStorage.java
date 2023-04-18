package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Film create(Film film);

    Collection<Film> findAll();

    Optional<Film> findById(long id);

    Film update(Film film);

    void deleteById(long id);
}
