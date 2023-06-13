package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDTO);

    ReviewDto updateReview(ReviewDto reviewDTO);

    void deleteReviewById(long reviewId);

    ReviewDto getReviewById(long reviewId);

    List<ReviewDto> getReviews(Long filmId, int count);

    void addLike(long reviewId, long userId);

    void addDislike(long reviewId, long userId);

    void deleteLike(long reviewId, long userId);

    void deleteDislike(long reviewId, long userId);
}
