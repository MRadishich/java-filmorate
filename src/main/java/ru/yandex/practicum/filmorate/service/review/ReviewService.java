package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);

    ReviewDTO updateReview(ReviewDTO reviewDTO);

    void deleteReviewById(long reviewId);

    ReviewDTO getReviewById(long reviewId);

    List<ReviewDTO> getReviews(Long filmId, int count);

    void addLike(long reviewId, long userId);

    void addDislike(long reviewId, long userId);

    void deleteLike(long reviewId, long userId);

    void deleteDislike(long reviewId, long userId);
}
