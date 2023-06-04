package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MapRowToGenre mapRowToGenre;

    @Override
    public Optional<Genre> findById(int id) {
        String sql = "SELECT * FROM genres WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, mapRowToGenre, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM genres";

        return jdbcTemplate.query(sql, mapRowToGenre);
    }

    @Override
    public List<Genre> findAllById(List<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = String.format("SELECT * FROM genres WHERE id IN (%s)", inSql);

        return jdbcTemplate.query(sql, mapRowToGenre, ids.toArray());
    }

    @Override
    public List<Genre> findAllByFilmId(Long filmId) {
        String sqlFindGenres = "SELECT " +
                "g.id, " +
                "g.name " +
                "FROM genres g " +
                "LEFT JOIN films_genres fg ON g.id = fg.genre_id " +
                "WHERE fg.film_id = ? " +
                "ORDER BY g.id";

        try {
            return jdbcTemplate.query(sqlFindGenres, mapRowToGenre, filmId);
        } catch (DataAccessException e) {
            return List.of();
        }
    }
}
