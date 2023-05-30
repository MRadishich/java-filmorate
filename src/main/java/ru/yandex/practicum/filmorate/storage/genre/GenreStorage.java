package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {
    Optional<Genre> findById(int id);

    List<Genre> findAll();

    List<Genre> findAllById(List<Integer> ids);

    List<Genre> findAllByFilm(Film film);
}
