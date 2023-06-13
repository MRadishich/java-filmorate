package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private final AtomicInteger filmId = new AtomicInteger();

    @Override
    public Film save(Film film) {
        if (film.getId() == null) {
            return update(film);
        }

        long id = filmId.incrementAndGet();

        film.setId(id);

        films.put(id, film);

        return film;
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> findById(long filmId) {

        return Optional.ofNullable(films.get(filmId));

    }

    @Override
    public List<Film> findAllById(List<Long> ids) {
        return films.entrySet().stream()
                .filter(k -> ids.contains(k.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private Film update(Film film) {
        if (existsById(film.getId())) {
            films.put(film.getId(), film);
            return film;
        }

        throw new NotFoundException(String.format("Фильм с id = %d не найден.", film.getId()));
    }

    @Override
    public void deleteById(long filmId) {
        if (films.remove(filmId) == null) {
            throw new NotFoundException(String.format("Фильм с id = %d не найден.", filmId));
        }
    }

    @Override
    public boolean existsById(long filmId) {
        return films.containsKey(filmId);
    }
}