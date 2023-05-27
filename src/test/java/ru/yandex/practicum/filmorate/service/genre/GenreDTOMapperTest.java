package ru.yandex.practicum.filmorate.service.genre;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.model.film.Genre;

import static org.junit.jupiter.api.Assertions.*;

class GenreDTOMapperTest {
    private final GenreDTOMapper genreDTOMapper = new GenreDTOMapper();

    @Test
    public void test1_shouldConvertedGenreToGenreDTO() {
        // Given
        Genre genre = new Genre(1, "Боевик");
        GenreDTO expectedGenreDTO = new GenreDTO(1, "Боевик");

        // When
        GenreDTO convertedGenreDTO = genreDTOMapper.apply(genre);

        // Then
        assertEquals(expectedGenreDTO, convertedGenreDTO);
    }
}