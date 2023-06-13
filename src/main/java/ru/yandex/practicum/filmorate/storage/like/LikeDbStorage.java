package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void deleteLikeFilm(long filmId, long userId) {
        String sql = "DELETE FROM likes " +
                "WHERE film_id = ? " +
                "AND user_id = ?";

        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void saveLikeFilm(long filmId, long userId) {
        String sql = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";

        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Long> findTopFilmIdByCountLikes(int limit) {
        String sql = "SELECT f.id " +
                "FROM films f " +
                "LEFT JOIN likes l ON f.id = l.film_id " +
                "GROUP BY f.id " +
                "ORDER BY COUNT(l.user_id) DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), limit);
    }

    @Override
    public void saveLikeReview(long reviewId, long userId) {
        String sql = "INSERT INTO reviews_likes (review_id, user_id, is_like) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, reviewId, userId, Boolean.TRUE);
    }

    @Override
    public void saveDislikeReview(long reviewId, long userId) {
        String sql = "INSERT INTO reviews_likes (review_id, user_id, is_like) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, reviewId, userId, Boolean.FALSE);
    }

    @Override
    public boolean deleteLikeReview(long reviewId, long userId) {
        String sql = "DELETE FROM reviews_likes " +
                "WHERE review_id = ? " +
                "AND user_id = ? ";

        return jdbcTemplate.update(sql, reviewId, userId) != 0;
    }

    @Override
    public boolean deleteDislikeReview(long reviewId, long userId) {
        String sql = "DELETE FROM reviews_likes " +
                "WHERE review_id = ?" +
                "AND user_id = ? ";

        return jdbcTemplate.update(sql, reviewId, userId) != 0;
    }
}
