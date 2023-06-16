package ru.yandex.practicum.filmorate.service.like;

public interface LikeService {
    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);
}
