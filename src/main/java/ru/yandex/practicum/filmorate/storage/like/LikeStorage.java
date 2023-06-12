package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.like.Like;

import java.util.List;

public interface LikeStorage {
    void save(Like like);

    void delete(Like like);

    List<Long> findTopFilmIdByCountLikes(int limit);
}
