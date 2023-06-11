package ru.yandex.practicum.filmorate.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dto.ReviewDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Review;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.review.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.reviewLike.ReviewLikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewStorage reviewStorage;
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private final ReviewLikeStorage reviewLikeStorage;

    private static final int INCREMENT = 1;
    private static final int DECREMENT = -1;

    @Override
    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        if (!userStorage.existsById(reviewDTO.getUserId())) {
            throw new NotFoundException("Пользователь с id = " + reviewDTO.getUseful() + " не найден.");
        }

        if (!filmStorage.existsById(reviewDTO.getFilmId())) {
            throw new NotFoundException("Фильм с id = " + reviewDTO.getFilmId() + " не найден.");
        }

        Review review = ReviewMapper.toReview(reviewDTO);
        review.setUseful(0);

        return ReviewMapper.toDto(reviewStorage.save(review));
    }

    @Override
    @Transactional
    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        Review review = ReviewMapper.toReview(reviewDTO);

        if (!reviewStorage.existsById(review.getId())) {
            throw new NotFoundException("Отзыв с id = " + review.getId() + " не найден.");
        }

        return ReviewMapper.toDto(reviewStorage.save(review));
    }

    @Override
    @Transactional
    public void deleteReviewById(Long reviewId) {
        if (!reviewStorage.existsById(reviewId)) {
            throw new NotFoundException("Отзыв с id = " + reviewId + " не найден.");
        }

        reviewStorage.deleteById(reviewId);
    }

    @Override
    @Transactional
    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewStorage.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Отзыв с id = " + reviewId + " не найден."));

        return ReviewMapper.toDto(review);
    }

    @Override
    @Transactional
    public List<ReviewDTO> getReviews(Long filmId, int count) {
        if (Objects.isNull(filmId)) {
            return reviewStorage.findMostUsefulReviews(count)
                    .stream()
                    .map(ReviewMapper::toDto)
                    .collect(Collectors.toList());
        }

        return reviewStorage.findMostUsefulReviewsByFilmId(filmId, count)
                .stream()
                .map(ReviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addLike(long reviewId, long userId) {
        if (!reviewStorage.existsById(reviewId)) {
            throw new NotFoundException("Отзыв с id = " + reviewId + " не найден.");
        }

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        reviewLikeStorage.addLike(reviewId, userId);
        reviewStorage.changeUseful(reviewId, INCREMENT);
    }

    @Override
    @Transactional
    public void addDislike(long reviewId, long userId) {
        if (!reviewStorage.existsById(reviewId)) {
            throw new NotFoundException("Отзыв с id = " + reviewId + " не найден.");
        }

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        reviewLikeStorage.addDislike(reviewId, userId);
        reviewStorage.changeUseful(reviewId, DECREMENT);
    }

    @Override
    @Transactional
    public void deleteLike(long reviewId, long userId) {
        if (!reviewStorage.existsById(reviewId)) {
            throw new NotFoundException("Отзыв с id = " + reviewId + " не найден.");
        }

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        if (!reviewLikeStorage.deleteLike(reviewId, userId)) {
            throw new NotFoundException("Пользователь с id = " + userId +
                    " не ставил лайк отзыву с id = " + reviewId + ".");
        }

        reviewStorage.changeUseful(reviewId, DECREMENT);
    }

    @Override
    @Transactional
    public void deleteDislike(long reviewId, long userId) {
        if (!reviewStorage.existsById(reviewId)) {
            throw new NotFoundException("Отзыв с id = " + reviewId + " не найден.");
        }

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        if (!reviewLikeStorage.deleteDislike(reviewId, userId)) {
            throw new NotFoundException("Пользователь с id = " + userId +
                    " не ставил дизлайк отзыву с id = " + reviewId + ".");
        }

        reviewStorage.changeUseful(reviewId, INCREMENT);
    }
}
