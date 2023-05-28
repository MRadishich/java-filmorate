package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.friendship.Friendship;
import ru.yandex.practicum.filmorate.model.friendship.FriendshipStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MapRowToFriendship implements RowMapper<Friendship> {
    @Override
    public Friendship mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Friendship(
                rs.getLong("user_id"),
                rs.getLong("friend_id"),
                FriendshipStatus.getStatus(rs.getInt("status")));
    }
}
