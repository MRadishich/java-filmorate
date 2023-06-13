package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MapRowToFilm mapRowToFilm;

    @Override
    @Transactional
    public Film save(Film film) {
        if (film.getId() == null) {
            return saveFilm(film);
        }

        return updateFilm(film);
    }


    private Film saveFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");

        Long filmId = simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue();

        film.setId(filmId);

        saveGenres(film);

        return film;
    }

    private void saveGenres(Film film) {
        String sql = "INSERT INTO films_genres (film_id, genre_id) VALUES (?, ?)";
        ArrayList<Genre> genres = new ArrayList<>(film.getGenres());

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, film.getId());
                ps.setInt(2, genres.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return genres.size();
            }
        });
    }

    private void deleteGenres(Long filmId) {
        String sqlDeleteGenres = "DELETE FROM films_genres " +
                "WHERE film_id = ?";

        jdbcTemplate.update(sqlDeleteGenres, filmId);
    }

    private Film updateFilm(Film film) {
        String sqlUpdateFilms = "UPDATE films " +
                "SET title = ?, " +
                "    description = ?, " +
                "    release_date = ?, " +
                "    duration = ?, " +
                "    mpa_rating_id = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sqlUpdateFilms,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );

        deleteGenres(film.getId());
        saveGenres(film);

        return film;
    }

    @Override
    @Transactional
    public Optional<Film> findById(long id) {
        String sql = "SELECT f.*, " +
                "mr.id AS mpa_rating_id, " +
                "mr.name AS mpa_rating_name, " +
                "mr.description AS mpa_rating_description " +
                "FROM films f " +
                "LEFT JOIN mpa_rating mr ON f.mpa_rating_id = mr.id " +
                "WHERE f.id = ?";

        Film film;

        try {
            film = jdbcTemplate.queryForObject(sql, mapRowToFilm, id);
            return Optional.ofNullable(film);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Film> findAll() {
        String sql = "SELECT f.*, " +
                "mr.id AS mpa_rating_id, " +
                "mr.name AS mpa_rating_name, " +
                "mr.description AS mpa_rating_description " +
                "FROM films f " +
                "LEFT JOIN mpa_rating mr ON f.mpa_rating_id = mr.id ";

        return jdbcTemplate.query(sql, mapRowToFilm);
    }

    @Override
    @Transactional
    public boolean existsById(long id) {
        return findById(id).isPresent();
    }

    @Override
    @Transactional
    public void deleteById(long filmId) {
        String sql = "DELETE FROM films WHERE id = ?";

        jdbcTemplate.update(sql, filmId);

        deleteGenres(filmId);
    }

    @Override
    @Transactional
    public List<Film> findAllById(List<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        String sql = String.format("SELECT f.*, " +
                "mr.id AS mpa_rating_id, " +
                "mr.name AS mpa_rating_name, " +
                "mr.description AS mpa_rating_description " +
                "FROM films f " +
                "LEFT JOIN mpa_rating mr ON f.mpa_rating_id = mr.id " +
                "WHERE f.id IN (%s)", inSql);

        return jdbcTemplate.query(sql, mapRowToFilm, ids.toArray());
    }
}
