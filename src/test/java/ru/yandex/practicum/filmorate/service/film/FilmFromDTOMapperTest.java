package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

public class FilmFromDTOMapperTest {
    private final FilmFromDTOMapper filmFromDTOMapper = new FilmFromDTOMapper();

    @Test
    public void test1_shouldConversionFilmDTOToFilm() {
        // Given
        FilmDTO filmDTO = new FilmDTO(
                1L,
                "The best film",
                LocalDate.now(),
                "The best film in your live",
                240,
                new Mpa(
                        1,
                        "G",
                        "General Audiences"
                ),
                List.of(new Genre(1, "Western"),
                        new Genre(2, "Romance"),
                        new Genre(3, "Horror")
                )
        );

        // When
        Film film = filmFromDTOMapper.apply(filmDTO);

        // Then
        assertEquals(filmDTO.getId(), film.getId());
        assertEquals(filmDTO.getName(), film.getName());
        assertEquals(filmDTO.getReleaseDate(), film.getReleaseDate());
        assertEquals(filmDTO.getDescription(), film.getDescription());
        assertEquals(filmDTO.getDuration(), film.getDuration());
        assertEquals(filmDTO.getMpa().getId(), film.getMpaId());
        assertEquals(filmDTO.getGenres(), film.getGenres());
    }
}
