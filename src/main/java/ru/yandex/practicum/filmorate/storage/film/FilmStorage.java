package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Film create(Film film);

    Collection<Film> findAll();

    Optional<Film> findById(long id);

    Collection<Film> findByIds(Collection<Long> ids);

    Film update(Film film);

    void deleteById(long id);

    boolean existById(long filmId);

    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    Collection<Film> findPopular(long count);
}
