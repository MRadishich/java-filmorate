package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryRepository implements FilmRepository {
    private static final Map<Integer, Film> films = new HashMap<>();
    private static final AtomicInteger filmId = new AtomicInteger();

    @Override
    public Film create(Film film) {
        int id = filmId.incrementAndGet();
        film.setId(id);
        films.put(id, film);
        return film;
    }

    @Override
    public Collection<Film> readAll() {
        return films.values();
    }

    @Override
    public Film read(int id) {
        Film film = films.get(id);
        if (film != null) {
            return film;
        }

        throw new FilmNotFoundException("Фильм не найден.");
    }

    @Override
    public Film update(Film film) {
        int filmId = film.getId();
        if (films.containsKey(filmId)) {
            Film oldFilm = films.get(filmId);
            films.put(filmId, film);
            return oldFilm;
        }

        throw new FilmNotFoundException("Фильм не найден");
    }

    @Override
    public void deleteAll() {
        films.clear();
    }

    @Override
    public boolean delete(int id) {
        return films.remove(id) != null;
    }
}
