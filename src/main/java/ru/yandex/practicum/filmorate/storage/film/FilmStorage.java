package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film save(Film film);

    Optional<Film> findById(long filmId);

    List<Film> findAll();

    boolean existsById(long id);

    void deleteById(long id);

    List<Film> findAllById(List<Long> ids);

    List<Film> findTopFilmsByCountLikes(int limit);
}
