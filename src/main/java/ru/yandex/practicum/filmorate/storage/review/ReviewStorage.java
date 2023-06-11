package ru.yandex.practicum.filmorate.storage.review;

import ru.yandex.practicum.filmorate.model.film.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewStorage {
    Review save(Review review);

    Optional<Review> findById(long id);

    List<Review> findMostUsefulReviews(int count);

    List<Review> findMostUsefulReviewsByFilmId(long filmId, int count);

    void deleteById(long id);

    boolean existsById(long id);

    void changeUseful(long reviewId, int value);
}
