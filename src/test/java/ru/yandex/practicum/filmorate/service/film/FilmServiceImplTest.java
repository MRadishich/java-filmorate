package ru.yandex.practicum.filmorate.service.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {
    @Mock
    private GenreStorage genreStorage;
    @Mock
    private FilmStorage filmStorage;
    @Mock
    private MpaStorage mpaStorage;
    private FilmService filmService;


    @BeforeEach
    void setUp() {
        filmService = new FilmServiceImpl(
                genreStorage,
                filmStorage,
                mpaStorage
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

        FilmDto filmDTO = new FilmDto(
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
                .mpa(new Mpa(1, null, null))
                .genres(List.of(
                        new Genre(1, null),
                        new Genre(2, null),
                        new Genre(3, null)
                ))
                .build();

        given(genreStorage.findAllById(anyList())).willReturn(genres);
        given(mpaStorage.findById(film.getMpa().getId())).willReturn(Optional.of(mpa));
        given(filmStorage.save(any(Film.class))).willReturn(film);

        // When
        FilmDto savedFilmDto = filmService.createFilm(filmDTO);

        // Then
        assertEquals(genres, savedFilmDto.getGenres());
        assertEquals(film.getId(), savedFilmDto.getId());
        assertEquals(filmDTO.getName(), savedFilmDto.getName());
        assertEquals(filmDTO.getDescription(), savedFilmDto.getDescription());
        assertEquals(filmDTO.getDuration(), savedFilmDto.getDuration());
        assertEquals(filmDTO.getReleaseDate(), savedFilmDto.getReleaseDate());
        assertEquals(mpa, savedFilmDto.getMpa());
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
                        .build(),
                new Film().toBuilder()
                        .id(2L)
                        .name("The best film 2")
                        .description("The best film in your live")
                        .duration(240)
                        .releaseDate(LocalDate.now())
                        .mpa(mpa)
                        .build(),
                new Film().toBuilder()
                        .id(3L)
                        .name("The best film 3")
                        .description("The best film in your live")
                        .duration(240)
                        .releaseDate(LocalDate.now())
                        .mpa(mpa)
                        .build()
        );

        List<FilmDto> expectedFilmDtos = List.of(
                new FilmDto(
                        1L,
                        "The best film 1",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                ),
                new FilmDto(
                        2L,
                        "The best film 2",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                ),
                new FilmDto(
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
        given(genreStorage.getGenresByFilms(films)).willReturn(Map.of(
                1L, genres,
                2L, genres,
                3L, genres
        ));

        // When
        Collection<FilmDto> foundFilmDtos = filmService.getAllFilms();

        // Then
        assertEquals(expectedFilmDtos, foundFilmDtos);

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

        FilmDto expectedFilmDto = new FilmDto(
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
                .build();

        given(filmStorage.findById(film.getId())).willReturn(Optional.of(film));
        given(genreStorage.findAllByFilmId(film.getId())).willReturn(genres);

        // When
        FilmDto foundFilmDto = filmService.getFilmById(1L);

        // Then
        assertEquals(expectedFilmDto, foundFilmDto);
    }

    @Test
    public void test4_shouldThrowNotFoundExceptionWhenTryFindFilmByUnknownId() {
        // Given
        Mpa mpa = new Mpa(1, "G", "У фильма нет возрастных ограничений");

        Film film = new Film().toBuilder()
                .id(1L)
                .name("The best film")
                .description("The best film in your live")
                .duration(240)
                .releaseDate(LocalDate.now())
                .mpa(mpa)
                .build();

        given(filmStorage.findById(anyLong())).willReturn(Optional.empty());

        String expectedMessage = "Фильм с id = " + film.getId() + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> filmService.getFilmById(film.getId()));

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

        FilmDto filmDTO = new FilmDto(
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
        given(genreStorage.findAllById(genres.stream().map(Genre::getId).collect(Collectors.toList()))).willReturn(genres);

        // When
        FilmDto updatedFilmDTo = filmService.updateFilm(filmDTO);

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

        FilmDto filmDTO = new FilmDto(
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
        long filmId = 1L;
        given(filmStorage.existsById(filmId)).willReturn(true);

        // When
        filmService.deleteFilmById(1L);

        // Then
        Mockito.verify(filmStorage, Mockito.times(1)).deleteById(filmId);
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
                        .build(),
                new Film().toBuilder()
                        .id(2L)
                        .name("The best film 2")
                        .description("The best film in your live")
                        .duration(240)
                        .releaseDate(LocalDate.now())
                        .mpa(mpa)
                        .build(),
                new Film().toBuilder()
                        .id(3L)
                        .name("The best film 3")
                        .description("The best film in your live")
                        .duration(240)
                        .releaseDate(LocalDate.now())
                        .mpa(mpa)
                        .build()
        );

        List<FilmDto> expectedFilmDtos = List.of(
                new FilmDto(
                        1L,
                        "The best film 1",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                ),
                new FilmDto(
                        2L,
                        "The best film 2",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                ),
                new FilmDto(
                        3L,
                        "The best film 3",
                        LocalDate.now(),
                        "The best film in your live",
                        240,
                        mpa,
                        genres
                )
        );


        given(filmStorage.findTopFilmsByCountLikes(3)).willReturn(films);
        given(genreStorage.getGenresByFilms(films)).willReturn(Map.of(
                1L, genres,
                2L, genres,
                3L, genres
        ));

        // When
        Collection<FilmDto> popularFilms = filmService.getTopFilmsByLikes(3);

        // Then
        assertEquals(expectedFilmDtos, popularFilms);
    }
}