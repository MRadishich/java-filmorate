package ru.yandex.practicum.filmorate.storage.reviewLike;

public interface ReviewLikeStorage {
    void addLike(long reviewId, long userId);

    void addDislike(long reviewId, long userId);

    boolean deleteLike(long reviewId, long userId);

    boolean deleteDislike(long reviewId, long userId);
}
