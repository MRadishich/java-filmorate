package ru.yandex.practicum.filmorate.storage.review;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.film.Review;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewDbStorage implements ReviewStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MapRowToReview mapRowToReview;

    @Override
    @Transactional
    public Review save(Review review) {
        if (review.getId() == null) {
            return saveReview(review);
        }

        return updateReview(review);
    }

    private Review updateReview(Review review) {
        String sqlUpdateReview = "UPDATE reviews " +
                "SET content = ?, " +
                "is_positive = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sqlUpdateReview,
                review.getContent(),
                review.getIsPositive(),
                review.getId()
        );

        return findById(review.getId()).get();
    }

    private Review saveReview(Review review) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reviews")
                .usingGeneratedKeyColumns("id");

        Long reviewId = simpleJdbcInsert.executeAndReturnKey(review.toMap()).longValue();

        review.setId(reviewId);

        return review;
    }

    @Override
    public Optional<Review> findById(long id) {
        String sql = "SELECT * FROM reviews " +
                "WHERE id = ?";

        try {
            Review review = jdbcTemplate.queryForObject(sql, mapRowToReview, id);
            return Optional.ofNullable(review);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Review> findMostUsefulReviews(int count) {
        String sql = "SELECT * FROM reviews " +
                "ORDER BY useful DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(sql, mapRowToReview, count);
    }

    @Override
    public List<Review> findMostUsefulReviewsByFilmId(long filmId, int limit) {
        String sql = "SELECT * FROM reviews " +
                "WHERE film_id = ? " +
                "ORDER BY useful DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(sql, mapRowToReview, filmId, limit);
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM reviews " +
                "WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

    @Override
    public void changeUseful(long reviewId, int value) {
        String sql = "UPDATE reviews " +
                "SET useful = useful + ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sql, value, reviewId);
    }
}
