package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.like.Like;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void delete(Like like) {
        String sql = "DELETE FROM likes " +
                "WHERE film_id = ? " +
                "AND user_id = ?";

        jdbcTemplate.update(sql, like.getFilmId(), like.getUserId());
    }

    @Override
    public void save(Like like) {
        String sql = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";

        jdbcTemplate.update(sql, like.getFilmId(), like.getUserId());
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
}
