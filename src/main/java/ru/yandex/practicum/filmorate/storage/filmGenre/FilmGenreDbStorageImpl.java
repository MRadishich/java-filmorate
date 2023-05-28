package ru.yandex.practicum.filmorate.storage.filmGenre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class FilmGenreDbStorageImpl implements FilmGenreDbStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<Long, List<Genre>> getAllFilmGenres(Collection<Film> films) {
        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));
        final String sql = String.format("SELECT " +
                "fg.film_id, " +
                "g.id AS genre_id, " +
                "g.name " +
                "FROM films_genres fg " +
                "LEFT JOIN genres g ON fg.genre_id = g.id " +
                "WHERE fg.film_id in (%s)", inSql);

        Map<Long, List<Genre>> filmGenresMap = new HashMap<>();

        jdbcTemplate.query(sql, rs -> {
            Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("name"));

            Long filmId = rs.getLong("film_id");

            filmGenresMap.putIfAbsent(filmId, new ArrayList<>());
            filmGenresMap.get(filmId).add(genre);
        }, films
                .stream()
                .map(film -> String.valueOf(film.getId()))
                .toArray());

        return filmGenresMap;
    }
}
