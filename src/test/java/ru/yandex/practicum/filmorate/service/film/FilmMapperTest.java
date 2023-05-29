package ru.yandex.practicum.filmorate.service.film;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FilmMapperTest {

    @Test
    public void test1_shouldConversionFilmToFilmDTO() {
        // Given
        Film film = new Film(
                1L,
                "The best film",
                "The vest film in your live",
                LocalDate.now(),
                240,
                new Mpa(1, "G", "General Audiences"),
                List.of(new Genre(1, "Western"),
                        new Genre(2, "Romance"),
                        new Genre(3, "Horror")
                )
        );

        Mpa mpa = new Mpa(1, "G", "General Audiences");

        // When calling the mocked repository method
        FilmDTO filmDTO = FilmMapper.toDto(film);

        // Then
        assertEquals(film.getId(), filmDTO.getId());
        assertEquals(film.getName(), filmDTO.getName());
        assertEquals(film.getDescription(), filmDTO.getDescription());
        assertEquals(film.getReleaseDate(), filmDTO.getReleaseDate());
        assertEquals(film.getDuration(), filmDTO.getDuration());
        assertEquals(film.getMpa(), filmDTO.getMpa());
        assertEquals(mpa, filmDTO.getMpa());
        assertEquals(film.getGenres(), filmDTO.getGenres());
    }

    @Test
    public void test2_shouldConversionFilmDTOToFilm() {
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
        Film film = FilmMapper.toFilm(filmDTO);

        // Then
        assertEquals(filmDTO.getId(), film.getId());
        assertEquals(filmDTO.getName(), film.getName());
        assertEquals(filmDTO.getReleaseDate(), film.getReleaseDate());
        assertEquals(filmDTO.getDescription(), film.getDescription());
        assertEquals(filmDTO.getDuration(), film.getDuration());
        assertEquals(filmDTO.getMpa(), film.getMpa());
        assertEquals(filmDTO.getGenres(), film.getGenres());
    }
}