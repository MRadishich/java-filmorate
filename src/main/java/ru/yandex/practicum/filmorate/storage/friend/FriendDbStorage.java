package ru.yandex.practicum.filmorate.storage.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.enums.FriendshipStatus;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(long userId, long friendId, FriendshipStatus status) {
        String sql = "INSERT INTO friendship VALUES(?, ?, ?)";

        jdbcTemplate.update(sql, userId, friendId, status.getValue());
    }

    @Override
    public boolean existsById(long userId, long friendId) {
        String sql = "SELECT COUNT() FROM friendship WHERE user_id = ? AND friend_id = ?";

        try {
            jdbcTemplate.queryForObject(sql, Integer.class, userId, friendId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public void delete(long userId, long friendId) {
        String sql = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";

        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<Long> findAllFriendIdsByUserId(long userId) {
        String sql = "SELECT friend_id FROM friendship WHERE user_id = ?";

        return jdbcTemplate.queryForList(sql, Long.class, userId);
    }
}
