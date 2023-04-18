package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage storage;

    public Film create(Film film) {
        log.info("Получен запрос на создание нового фильма: {}", film);

        return storage.create(film);
    }

    public Collection<Film> findAll() {
        log.info("Получен запрос на поиск всех фильмов.");

        return storage.findAll();
    }

    public Film findById(long id) {
        log.info("Получен запрос на поиск фильма по id = {}", id);

        return storage.findById(id).orElseThrow(() -> new NotFoundException(String.format("Фильм  с id = %d не найден.", id)));
    }

    public Film update(Film film) {
        log.info("Получен запрос на обновление фильма с id = {}. Новое значение: {}", film.getId(), film);

        return storage.update(film);
    }

    public void deleteById(long id) {
        log.info("Получен запрос на удаление фильма с id = {}.", id);

        storage.deleteById(id);
    }
}
