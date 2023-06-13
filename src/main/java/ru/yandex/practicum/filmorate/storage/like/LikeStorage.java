package ru.yandex.practicum.filmorate.storage.like;

import java.util.List;

public interface LikeStorage {
    void saveLikeFilm(long filmId, long userId);

    void deleteLikeFilm(long filmId, long userId);

    List<Long> findTopFilmIdByCountLikes(int limit);

    void saveLikeReview(long reviewId, long userId);

    void saveDislikeReview(long reviewId, long userId);

    boolean deleteLikeReview(long reviewId, long userId);

    boolean deleteDislikeReview(long reviewId, long userId);
}
