package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.dto.ReviewDTO;
import ru.yandex.practicum.filmorate.model.film.Review;

public class ReviewMapper {
    public static ReviewDTO toDto(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId(),
                review.getUseful()
        );
    }

    public static Review toReview(ReviewDTO reviewDTO) {
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
