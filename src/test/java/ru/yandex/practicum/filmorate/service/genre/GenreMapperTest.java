package ru.yandex.practicum.filmorate.service.genre;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenreMapperTest {

    @Test
    public void test1_shouldConvertedGenreToGenreDTO() {
        // Given
        Genre genre = new Genre(1, "Боевик");
        GenreDto expectedGenreDTO = new GenreDto(1, "Боевик");

        // When
        GenreDto convertedGenreDTO = GenreMapper.toDto(genre);

        // Then
        assertEquals(expectedGenreDTO, convertedGenreDTO);
    }
}