package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDbStorageImpl implements UserDbStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MapRowToUser mapRowToUser;

    @Override
    @Transactional
    public User save(User user) {
        if (user.getId() == null) {
            return saveUser(user);
        }

        return updateUser(user);
    }


    private User saveUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        Long userId = simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();
        user.setId(userId);

        return user;
    }

    private User updateUser(User user) {
        String sqlQueryUpdateUser = "UPDATE users " +
                "SET name = ?, " +
                "    email = ?, " +
                "    login = ?, " +
                "    birthday = ? " +
                "where id = ?";

        jdbcTemplate.update(sqlQueryUpdateUser,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId());

        return user;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        String sql = "SELECT * FROM users";

        return jdbcTemplate.query(sql, mapRowToUser);
    }

    @Override
    @Transactional
    public Optional<User> findById(Long userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, mapRowToUser, userId));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public boolean existsById(Long userId) {
        return findById(userId).isPresent();
    }

    @Override
    public void deleteById(long userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        jdbcTemplate.update(sql, userId);
    }

    @Override
    public List<User> findAllById(List<Long> ids) {
        String inSql = String.join(", ", Collections.nCopies(ids.size(), "?"));
        String sql = String.format("SELECt * FROM users WHERE id IN (%s)", inSql);

        return jdbcTemplate.query(sql, mapRowToUser, ids.toArray());
    }
}
