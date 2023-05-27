package ru.yandex.practicum.filmorate.service.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FilmToDTOMapperTest {
    @Mock
    private MpaDbStorage mpaStorage;
    private FilmToDTOMapper filmToDTO;

    @BeforeEach
    void setUp() {
        filmToDTO = new FilmToDTOMapper(mpaStorage);
    }

    @Test
    public void shouldConversionFilmToFilmDTO() {
        // Given
        Film film = new Film(
                1L,
                "The best film",
                "The vest film in your live",
                LocalDate.now(),
                240,
                1,
                List.of(new Genre(1, "Western"),
                        new Genre(2, "Romance"),
                        new Genre(3, "Horror")
                )
        );

        Mpa mpa = new Mpa(1, "G", "General Audiences");

        // When calling the mocked repository method
        given(mpaStorage.findById(1)).willReturn(Optional.of(mpa));
        FilmDTO filmDTO = filmToDTO.apply(film);

        // Then
        assertEquals(film.getId(), filmDTO.getId());
        assertEquals(film.getName(), filmDTO.getName());
        assertEquals(film.getDescription(), filmDTO.getDescription());
        assertEquals(film.getReleaseDate(), filmDTO.getReleaseDate());
        assertEquals(film.getDuration(), filmDTO.getDuration());
        assertEquals(film.getMpaId(), filmDTO.getMpa().getId());
        assertEquals(mpa, filmDTO.getMpa());
        assertEquals(film.getGenres(), filmDTO.getGenres());
    }
}