package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Slf4j
public class InMemoryFilmsStorage implements FilmStorage {
    private static final Map<Long, Film> films = new HashMap<>();
    private static final AtomicInteger filmId = new AtomicInteger();
    private static final String MESSAGE = "Фильм  с id = %s не найден.";

    @Override
    public Film create(Film film) {
        long id = filmId.incrementAndGet();

        film.setId(id);

        films.put(id, film);

        log.info("Создан новый фильм: {}", film);
        return film;
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film findById(long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        log.error("Запрос неизвестного фильма с id = {}.", id);
        throw new NotFoundException(String.format(MESSAGE, id));
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);

            log.info("Обновлен фильм с id = {}. Новое значение: {}", film.getId(), film);
            return film;
        }

        log.error("Попытка обновить неизвестный фильм с id = {}.", film.getId());
        throw new NotFoundException(String.format(MESSAGE, film.getId()));
    }

    @Override
    public void deleteById(long id) {
        if (films.remove(id) == null) {
            log.error("Попытка удалить неизвестный фильм с id = {}.", id);
            throw new NotFoundException(String.format(MESSAGE, id));
        }

        log.info("Удален фильм с id = {}.", id);
    }
}
