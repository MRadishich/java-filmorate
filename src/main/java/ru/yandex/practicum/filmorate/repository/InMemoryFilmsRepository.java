package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryFilmsRepository implements FilmRepository {
    private static final Map<Integer, Film> films = new HashMap<>();
    private static final AtomicInteger filmId = new AtomicInteger();
    private static final String MESSAGE = "Фильм  с id = %s не найден.";

    @Override
    public Film create(Film film) {
        int id = filmId.incrementAndGet();
        film.setId(id);
        films.put(id, film);

        return film;
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public Film get(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }

        throw new NotFoundException(String.format(MESSAGE, id));
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        }

        throw new NotFoundException(String.format(MESSAGE, film.getId()));
    }

    @Override
    public void delete(int id) {
        if (films.remove(id) == null) {
            throw new NotFoundException(String.format(MESSAGE, id));
        }
    }
}
