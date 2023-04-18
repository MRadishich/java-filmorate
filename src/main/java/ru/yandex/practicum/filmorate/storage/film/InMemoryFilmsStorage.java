package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryFilmsStorage implements FilmStorage {
    private static final Map<Long, Film> films = new HashMap<>();
    private static final AtomicInteger filmId = new AtomicInteger();

    @Override
    public Film create(Film film) {
        long id = filmId.incrementAndGet();

        film.setId(id);

        films.put(id, film);

        return film;
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Optional<Film> findById(long id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        }

        throw new NotFoundException(String.format("Фильм  с id = %d не найден.", film.getId()));
    }

    @Override
    public void deleteById(long id) {
        if (films.remove(id) == null) {
            throw new NotFoundException(String.format("Фильм  с id = %d не найден.", id));
        }
    }
}
