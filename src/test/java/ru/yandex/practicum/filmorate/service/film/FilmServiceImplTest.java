package ru.yandex.practicum.filmorate.service.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.service.like.LikeService;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {
    @Mock
    private GenreDbStorage genreStorage;
    @Mock
    private FilmDbStorage filmStorage;
    @Mock
    private LikeService likeService;
    private FilmService filmService;


    @BeforeEach
    void setUp() {
        filmService = new FilmServiceImpl(
                genreStorage,
                filmStorage,
                likeService
        );
    }

    @Test
    public void test1_shouldSaveNewFilmInStorage() {
        // Given
        List<Genre> genres = List.of(
                new Genre(1, "Боевик"),
                new Genre(2, "Комедия"),
                new Genre(3, "Ужасы")
        );
        Mpa mpa = new Mpa(1, "G", "У фильма нет возрастных ограничений");

        FilmDTO filmDTO = new FilmDTO(
                1L,
                "The best film",
                LocalDate.now(),
                "The best film in your live",
                240,
                new Mpa(
                        1,
                        null,
                        null
                ),
                List.of(
                        new Genre(1, null),
                        new Genre(2, null),
                        new Genre(3, null)
                )
        );

        Film film = new Film().toBuilder()
                .id(1L)
                .name("The best film")
                .description("The best film in your live")
                .duration(240)
                .releaseDate(LocalDate.now())
                .mpa(new Mpa(1, "G", "У фильма нет возрастных ограничений"))
                .genres(genres)
                .build();

        given(genreStorage.findAllById(anyList())).willReturn(genres);
        given(filmStorage.save(any(Film.class))).willReturn(film);

        // When
        FilmDTO savedFilmDTO = filmService.createFilm(filmDTO);

        // Then
        assertEquals(genres, savedFilmDTO.getGenres());
        assertEquals(film.getId(), savedFilmDTO.getId());
        assertEquals(filmDTO.getName(), savedFilmDTO.getName());
        assertEquals(filmDTO.getDescription(), savedFilmDTO.getDescription());
        assertEquals(filmDTO.getDuration(), savedFilmDTO.getDuration());
        assertEquals(filmDTO.getReleaseDate(), savedFilmDTO.getReleaseDate());
        assertEquals(mpa, savedFilmDTO.getMpa());
    }


    @Test
    public void test2_shouldReturnAllFilmFromStorage() {
        // Given
        List<Genre> genres = List.of(
                new Genre(1, "Боевик"),
                new Genre(2, "Комедия"),
                new Genre(3, "Ужасы")
        );
        Mpa mpa = new Mpa(1, "G", "У фильма нет возрастных ограничений");

        List<Film> films = List.of(
                new Film().toBuilder()
                        .id(1L)
                        .name("The best film 1")
                        .description("The best film in your live")
                        .duration(240)
                        .releaseDate(LocalDate.now())
                        .mpa(mpa)
                        .genres(genres)
                        .build(),
                new Film().toBuilder()
                        .id(2L)
                        .name("The best film 2")
                        .description("The best film in your live")
                        .duration(240)
                        .releaseDate(LocalDate.now())
                        .mpa(mpa)
                        .genres(genres)
                        .build(),
                new Film().toBuilder()
                        .id(3L)
                        .name("The best film 3")
                        .description("The best film in your live")
                        .duration(240)
                        .releaseDate(LocalDate.now())
                        .mpa(mpa)
                        .genres(genres)
                        .build()
        );

        List<FilmDTO> expectedFilmDTOs = List.of(
                new FilmDTO(
                        1L,
                        "The best film 1",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                ),
                new FilmDTO(
                        2L,
                        "The best film 2",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                ),
                new FilmDTO(
                        3L,
                        "The best film 3",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                )
        );

        given(filmStorage.findAll()).willReturn(films);

        // When
        Collection<FilmDTO> foundFilmDTOs = filmService.findAllFilms();

        // Then
        assertEquals(expectedFilmDTOs, foundFilmDTOs);

    }

    @Test
    public void test3_shouldReturnFilmById() {
        // Given
        List<Genre> genres = List.of(
                new Genre(1, "Боевик"),
                new Genre(2, "Комедия"),
                new Genre(3, "Ужасы")
        );
        Mpa mpa = new Mpa(1, "G", "У фильма нет возрастных ограничений");

        FilmDTO expectedFilmDTO = new FilmDTO(
                1L,
                "The best film",
                LocalDate.now(),
                "The best film in your live",
                240,
                mpa,
                genres
        );

        Film film = new Film().toBuilder()
                .id(1L)
                .name("The best film")
                .description("The best film in your live")
                .duration(240)
                .releaseDate(LocalDate.now())
                .mpa(mpa)
                .genres(genres)
                .build();

        given(filmStorage.findById(anyLong())).willReturn(Optional.of(film));

        // When
        FilmDTO foundFilmDTO = filmService.findFilmById(1L);

        // Then
        assertEquals(expectedFilmDTO, foundFilmDTO);
    }

    @Test
    public void test4_shouldThrowNotFoundExceptionWhenTryFindFilmByUnknownId() {
        // Given
        List<Genre> genres = List.of(
                new Genre(1, "Боевик"),
                new Genre(2, "Комедия"),
                new Genre(3, "Ужасы")
        );
        Mpa mpa = new Mpa(1, "G", "У фильма нет возрастных ограничений");

        Film film = new Film().toBuilder()
                .id(1L)
                .name("The best film")
                .description("The best film in your live")
                .duration(240)
                .releaseDate(LocalDate.now())
                .mpa(mpa)
                .genres(genres)
                .build();

        given(filmStorage.findById(anyLong())).willReturn(Optional.empty());

        String expectedMessage = "Фильм с id = " + film.getId() + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> filmService.findFilmById(film.getId()));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test5_shouldUpdateFilmIfFilmExists() {
        // Given
        List<Genre> genres = List.of(
                new Genre(1, "Боевик"),
                new Genre(2, "Комедия"),
                new Genre(3, "Ужасы")
        );
        Mpa mpa = new Mpa(1, "G", "У фильма нет возрастных ограничений");

        FilmDTO filmDTO = new FilmDTO(
                1L,
                "Updated the best film",
                LocalDate.now(),
                "Updated the best film in your live",
                240,
                mpa,
                genres
        );

        Film film = new Film().toBuilder()
                .id(1L)
                .name("Updated the best film")
                .description("Updated the best film in your live")
                .duration(240)
                .releaseDate(LocalDate.now())
                .mpa(mpa)
                .genres(genres)
                .build();

        given(filmStorage.existsById(film.getId())).willReturn(true);
        given(filmStorage.save(any(Film.class))).willReturn(film);

        // When
        FilmDTO updatedFilmDTo = filmService.updateFilm(filmDTO);

        // Then
        assertEquals(filmDTO, updatedFilmDTo);
    }

    @Test
    public void test6_shouldThrowNotFoundExceptionWhenTryUpdateUnknownFilm() {
        // Given
        List<Genre> genres = List.of(
                new Genre(1, "Боевик"),
                new Genre(2, "Комедия"),
                new Genre(3, "Ужасы")
        );
        Mpa mpa = new Mpa(1, "G", "У фильма нет возрастных ограничений");

        FilmDTO filmDTO = new FilmDTO(
                1L,
                "Updated the best film",
                LocalDate.now(),
                "Updated the best film in your live",
                240,
                mpa,
                genres
        );

        Film film = new Film().toBuilder()
                .id(1L)
                .name("Updated the best film")
                .description("Updated the best film in your live")
                .duration(240)
                .releaseDate(LocalDate.now())
                .mpa(mpa)
                .genres(genres)
                .build();

        given(filmStorage.existsById(film.getId())).willReturn(false);

        String expectedMessage = "Фильм с id = " + film.getId() + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> filmService.updateFilm(filmDTO));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    public void test7_shouldDeleteFilmById() {
        // Given
        given(filmStorage.existsById(any())).willReturn(true);

        // When
        filmService.deleteFilmById(1L);

        // Then
        Mockito.verify(filmStorage, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void test8_shouldThrowNotFoundExceptionWhenTryDeleteFilmByUnknownId() {
        // Given
        given(filmStorage.existsById(anyLong())).willReturn(false);
        String expectedMessage = "Фильм с id = 1 не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> filmService.deleteFilmById(1L));

        // Then
        Mockito.verify(filmStorage, Mockito.times(1)).existsById(1L);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test9_shouldReturnTopPopularFilms() {
        // Given
        List<Genre> genres = List.of(
                new Genre(1, "Боевик"),
                new Genre(2, "Комедия"),
                new Genre(3, "Ужасы")
        );
        Mpa mpa = new Mpa(1, "G", "У фильма нет возрастных ограничений");

        List<Film> films = List.of(
                new Film().toBuilder()
                        .id(1L)
                        .name("The best film 1")
                        .description("The best film in your live")
                        .duration(240)
                        .releaseDate(LocalDate.now())
                        .mpa(mpa)
                        .genres(genres)
                        .build(),
                new Film().toBuilder()
                        .id(2L)
                        .name("The best film 2")
                        .description("The best film in your live")
                        .duration(240)
                        .releaseDate(LocalDate.now())
                        .mpa(mpa)
                        .genres(genres)
                        .build(),
                new Film().toBuilder()
                        .id(3L)
                        .name("The best film 3")
                        .description("The best film in your live")
                        .duration(240)
                        .releaseDate(LocalDate.now())
                        .mpa(mpa)
                        .genres(genres)
                        .build()
        );

        List<FilmDTO> expectedFilmDTOs = List.of(
                new FilmDTO(
                        1L,
                        "The best film 1",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                ),
                new FilmDTO(
                        2L,
                        "The best film 2",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                ),
                new FilmDTO(
                        3L,
                        "The best film 3",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                )
        );


        given(likeService.findTopFilmsByLikes(3)).willReturn(List.of(1L, 2L, 3L));
        given(filmStorage.findAllById(anyList())).willReturn(films);

        // When
        Collection<FilmDTO> popularFilms = filmService.findPopularFilms(3);

        // Then
        assertEquals(expectedFilmDTOs, popularFilms);
    }
}