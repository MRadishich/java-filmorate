package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface GenreDbStorage {
    Optional<Genre> findById(int id);

    List<Genre> findAll();

    List<Genre> findAllById(List<Integer> ids);

    List<Genre> findAllByFilm(Film film);
}
