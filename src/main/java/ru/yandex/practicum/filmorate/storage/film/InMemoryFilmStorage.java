package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryFilmStorage implements FilmStorage {
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
    public Film findById(long filmId) {
        Film film = films.get(filmId);

        if (film == null) {
            throw new NotFoundException(String.format("Фильм  с id = %d не найден.", filmId));
        }

        return film;
    }

    @Override
    public Collection<Film> findByIds(Collection<Long> ids) {
        return films.entrySet().stream()
                .filter(k -> ids.contains(k.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public Film update(Film film) {
        if (existById(film.getId())) {
            films.put(film.getId(), film);
            return film;
        }

        throw new NotFoundException(String.format("Фильм  с id = %d не найден.", film.getId()));
    }

    @Override
    public void deleteById(long filmId) {
        if (films.remove(filmId) == null) {
            throw new NotFoundException(String.format("Фильм  с id = %d не найден.", filmId));
        }
    }

    @Override
    public boolean existById(long filmId) {
        return films.containsKey(filmId);
    }
}
