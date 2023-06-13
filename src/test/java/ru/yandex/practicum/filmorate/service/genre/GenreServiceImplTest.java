package ru.yandex.practicum.filmorate.service.genre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {
    @Mock
    private GenreStorage genreStorage;
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreStorage);
    }

    @Test
    public void test1_shouldReturnGenreDTOByGenreId() {
        // Given
        Genre genre = new Genre(1, "Боевик");
        GenreDto expectedGenreDTO = new GenreDto(1, "Боевик");

        given(genreStorage.findById(genre.getId())).willReturn(Optional.of(genre));

        // When
        GenreDto foundGenreDTO = genreService.getById(genre.getId());

        // Then
        assertEquals(expectedGenreDTO, foundGenreDTO);
    }

    @Test
    public void test2_shouldThrowNotFoundExceptionWhenUnknownGenreId() {
        // Given
        Genre genre = new Genre(1, "Боевик");

        given(genreStorage.findById(genre.getId())).willReturn(Optional.empty());
        String expectedMessage = "Жанр с id = " + genre.getId() + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> genreService.getById(genre.getId()));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test3_shouldReturnAllMpaConvertedDTO() {
        // Given
        List<Genre> genres = List.of(
                new Genre(1, "Боевик"),
                new Genre(2, "Ужасы"),
                new Genre(3, "Комедия")
        );
        List<GenreDto> expectedGenreDTOS = List.of(
                new GenreDto(1, "Боевик"),
                new GenreDto(2, "Ужасы"),
                new GenreDto(3, "Комедия")
        );

        given(genreStorage.findAll()).willReturn(genres);

        // When
        Collection<GenreDto> foundGenreDTOs = genreService.getAll();

        // Then
        assertEquals(expectedGenreDTOS, foundGenreDTOs);
    }
}