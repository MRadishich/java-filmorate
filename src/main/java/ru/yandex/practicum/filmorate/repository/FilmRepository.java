package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmRepository {
    Film create(Film film);

    Collection<Film> readAll();

    Film read(int id);

    Film update(Film film);

    void deleteAll();

    boolean delete(int id);
}
