package ru.yandex.practicum.filmorate.storage.reviewLike;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewLikeDbStorage implements ReviewLikeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(long reviewId, long userId) {
        String sql = "INSERT INTO reviews_likes (review_id, user_id, is_like) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, reviewId, userId, Boolean.TRUE);
    }

    @Override
    public void addDislike(long reviewId, long userId) {
        String sql = "INSERT INTO reviews_likes (review_id, user_id, is_like) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, reviewId, userId, Boolean.FALSE);
    }

    @Override
    public boolean deleteLike(long reviewId, long userId) {
        String sql = "DELETE FROM reviews_likes " +
                "WHERE review_id = ? " +
                "AND user_id = ? ";

        return jdbcTemplate.update(sql, reviewId, userId) != 0;
    }

    @Override
    public boolean deleteDislike(long reviewId, long userId) {
        String sql = "DELETE FROM reviews_likes " +
                "WHERE review_id = ?" +
                "AND user_id = ? ";

        return jdbcTemplate.update(sql, reviewId, userId) != 0;
    }
}
