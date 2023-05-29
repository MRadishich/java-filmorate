package ru.yandex.practicum.filmorate.storage.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.friendship.Friendship;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MapRowToFriendship mapRowToFriendship;

    @Override
    public void save(Friendship friendship) {
        String sql = "INSERT INTO friendship VALUES(?, ?, ?)";

        jdbcTemplate.update(sql, friendship.getUserId(), friendship.getFriendId(), friendship.getStatus().getValue());
    }

    @Override
    public boolean existsById(Friendship friendship) {
        String sql = "SELECT * FROM friendship WHERE user_id = ? AND friend_id = ?";

        try {
            jdbcTemplate.queryForObject(sql, mapRowToFriendship, friendship.getUserId(), friendship.getFriendId());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public void delete(Friendship friendship) {
        String sql = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";

        jdbcTemplate.update(sql, friendship.getUserId(), friendship.getFriendId());
    }

    @Override
    public List<Friendship> findAllByUserId(Long userId) {
        String sql = "SELECt * FROM friendship WHERE user_id = ?";

        return jdbcTemplate.query(sql, mapRowToFriendship, userId);
    }
}
