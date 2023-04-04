package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmRepository {
    int create(Film film);

    Collection<Film> getAll();

    Film get(int id);

    boolean update(Film film);

    boolean delete(int id);
}
