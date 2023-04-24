package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryFilmsStorage implements FilmStorage {
    private static final Map<Long, Film> films = new HashMap<>();
    private static final Map<Long, Set<Long>> likes = new HashMap<>();
    private static final AtomicInteger filmId = new AtomicInteger();

    @Override
    public Film create(Film film) {
        long id = filmId.incrementAndGet();

        film.setId(id);

        films.put(id, film);
        likes.put(id, new HashSet<>());

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

    @Override
    public void addLike(long filmId, long userId) {
        if (existById(filmId)) {
            likes.get(filmId).add(userId);
        } else {
            throw new NotFoundException(String.format("Фильм  с id = %d не найден.", filmId));
        }
    }

    public void deleteLike(long filmId, long userId) {
        if (!existById(filmId)) {
            throw new NotFoundException(String.format("Фильм  с id = %d не найден.", filmId));
        }

        if (!likes.get(filmId).remove(userId)) {
            throw new NotFoundException(String.format("Лайк от пользователя  с id = %d не найден.", userId));
        }
    }

    public Collection<Film> findPopular(long count) {
        return findByIds(likes.entrySet().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getValue().size(), f1.getValue().size()))
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList())
        );
    }
}
