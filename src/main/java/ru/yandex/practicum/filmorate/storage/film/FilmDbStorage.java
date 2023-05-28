package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;
import java.util.Optional;

public interface FilmDbStorage {
    Film save(Film film);

    Optional<Film> findById(Long filmId);

    List<Film> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Film> findAllById(List<Long> ids);
}
