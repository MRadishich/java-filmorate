package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.stream.Collectors;

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

    public void like(long filmId, long userId) {
        log.info("Получен запрос на добавление лайка фильму с id = {}, от пользователя с id = {}.", filmId, userId);

        storage.findById(filmId).ifPresentOrElse(
                film -> film.addLike(userId),
                () -> {
                    throw new NotFoundException(String.format("Фильм  с id = %d не найден.", filmId));
                }
        );
    }

    public void deleteLike(long filmId, long userId) {
        log.info("Получен запрос на удаление лайка у фильма с id = {}, от пользователя с id = {}.", filmId, userId);

        storage.findById(filmId).ifPresentOrElse(
                film -> film.deleteLike(userId),
                () -> {
                    throw new NotFoundException(String.format("Фильм  с id = %d не найден.", filmId));
                }
        );
    }

    public Collection<Film> findPopular(long count) {
        log.info("Получен запрос на поиск {} фильмов с наибольшим количеством лайков.", count);

        return storage.findAll().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
