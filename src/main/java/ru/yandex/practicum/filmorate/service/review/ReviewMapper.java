package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;

public class ReviewMapper {
    public static ReviewDto toDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId(),
                review.getUseful()
        );
    }

    public static Review toReview(ReviewDto reviewDTO) {
        return new Review(
                reviewDTO.getReviewId(),
                reviewDTO.getContent(),
                reviewDTO.getIsPositive(),
                reviewDTO.getUserId(),
                reviewDTO.getFilmId(),
                reviewDTO.getUseful()
        );
    }
}
