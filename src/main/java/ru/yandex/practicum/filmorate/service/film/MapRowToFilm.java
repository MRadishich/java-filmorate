package ru.yandex.practicum.filmorate.service.film;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class MapRowToFilm implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Film().toBuilder()
                .id(rs.getLong("id"))
                .name(rs.getString("title"))
                .description(rs.getString("description"))
                .releaseDate(LocalDate.parse(rs.getString("release_date")))
                .duration(rs.getInt("duration"))
                .mpaId(rs.getInt("mpa_rating_id"))
                .build();
    }
}
