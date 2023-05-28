package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class MapRowToUser implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User().toBuilder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .login(rs.getString("login"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .birthday(LocalDate.parse(rs.getString("birthday")))
                .build();
    }
}
