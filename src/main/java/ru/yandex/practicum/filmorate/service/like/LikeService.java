package ru.yandex.practicum.filmorate.service.like;

import java.util.List;


public interface LikeService {
    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    List<Long> getTopFilmsByLikes(int limit);
}
