package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;

    public Film create(Film film) {
        log.info("Получен запрос на создание нового фильма: {}", film);

        Film newFilm = filmStorage.create(film);
        likeStorage.create(film.getId());

        return newFilm;
    }

    public Collection<Film> findAll() {
        log.info("Получен запрос на поиск всех фильмов.");

        return filmStorage.findAll();
    }

    public Film findById(long id) {
        log.info("Получен запрос на поиск фильма по id = {}", id);

        return filmStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Фильм  с id = %d не найден.", id)));
    }

    public Film update(Film film) {
        log.info("Получен запрос на обновление фильма с id = {}. Новое значение: {}", film.getId(), film);

        return filmStorage.update(film);
    }

    public void deleteById(long filmId) {
        log.info("Получен запрос на удаление фильма с filmId = {}.", filmId);

        filmStorage.deleteById(filmId);
    }

    public void addLike(long filmId, long userId) {
        log.info("Получен запрос на добавление лайка фильму с id = {}, от пользователя с id = {}.", filmId, userId);

        if (!filmStorage.existById(filmId)) {
            throw new NotFoundException(String.format("Фильм  с id = %d не найден.", filmId));
        }

        likeStorage.addLike(filmId, userId);
    }

    public void deleteLike(long filmId, long userId) {
        log.info("Получен запрос на удаление лайка у фильма с id = {}, от пользователя с id = {}.", filmId, userId);

        if (!filmStorage.existById(filmId)) {
            throw new NotFoundException(String.format("Фильм  с id = %d не найден.", filmId));
        }

        likeStorage.deleteLike(filmId, userId);
    }

    public Collection<Film> findPopular(long count) {
        log.info("Получен запрос на поиск {} фильмов с наибольшим количеством лайков.", count);

        return filmStorage.findByIds(likeStorage.findPopular(count));
    }
}
