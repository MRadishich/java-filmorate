package ru.yandex.practicum.filmorate.storage.likes;

import java.util.Collection;

public interface LikeStorage {
    void create(long filmId);

    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    Collection<Long> findPopular(long count);
}
