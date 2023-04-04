package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryFilmsRepository implements FilmRepository {
    private static final Map<Integer, Film> films = new HashMap<>();
    private static final AtomicInteger filmId = new AtomicInteger();

    @Override
    public int create(Film film) {
        int id = filmId.incrementAndGet();
        film.setId(id);
        films.put(id, film);

        return id;
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public Film get(int id) {
        return films.get(id);
    }

    @Override
    public boolean update(Film film) {
        int filmId = film.getId();
        if (films.containsKey(filmId)) {
            films.put(filmId, film);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        return films.remove(id) != null;
    }
}
