package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MapRowToMpa mapRowToMpa;

    @Override
    public Optional<Mpa> findById(int mpaId) {
        String sql = "SELECT * FROM mpa_rating WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, mapRowToMpa, mpaId));
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Mpa> findAll() {
        String sql = "SELECT * FROM mpa_rating";

        return jdbcTemplate.query(sql, mapRowToMpa);
    }
}
