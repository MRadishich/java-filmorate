package ru.yandex.practicum.filmorate.service.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.like.Like;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeDbStorage likeStorage;
    private final FilmDbStorage filmStorage;
    private final UserDbStorage userStorage;

    @Override
    public void addLike(long filmId, long userId) {
        log.info("Получен запрос на добавление лайка фильму с id = {}, от пользователя с id = {}.", filmId, userId);

        if (!filmStorage.existsById(filmId)) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден.");
        }

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        likeStorage.save(new Like(filmId, userId));
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        log.info("Получен запрос на удаление лайка у фильма с id = {}, от пользователя с id = {}.", filmId, userId);

        if (!filmStorage.existsById(filmId)) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден.");
        }

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        likeStorage.delete(new Like(filmId, userId));
    }

    @Override
    public List<Long> findTopFilmsByLikes(int limit) {
        log.info("Получен запрос на поиск {} фильмов с наибольшим количеством лайков.", limit);

        return likeStorage.findTopFilmIdByCountLikes(PageRequest.of(0, limit));
    }
}
