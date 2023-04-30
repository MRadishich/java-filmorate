package ru.yandex.practicum.filmorate.service.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.like.InMemoryLikeStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmServiceTest {
    private FilmStorage filmStorage;
    private LikeStorage likeStorage;
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmStorage = Mockito.mock(FilmStorage.class);
        likeStorage = Mockito.mock(LikeStorage.class);
        filmService = new FilmServiceImpl(filmStorage, likeStorage);
    }

    @Test
    void test1_shouldSaveNewFilmInStorage() {
        Film film = new Film().toBuilder()
                .id(1L)
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmService.create(film);

        Mockito.verify(filmStorage, Mockito.times(1)).create(film);
        Mockito.verify(likeStorage, Mockito.times(1)).create(1L);
    }

    @Test
    void test2_shouldReturnAllFilmFromStorage() {
        filmService.findAll();

        Mockito.verify(filmStorage, Mockito.times(1)).findAll();
    }

    @Test
    void test3_shouldReturnFilmById() {
        filmService.findById(1L);

        Mockito.verify(filmStorage, Mockito.times(1)).findById(1L);
    }

    @Test
    void test4_shouldUpdateFilm() {
        Film film = new Film().toBuilder()
                .id(1L)
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmService.update(film);

        Mockito.verify(filmStorage, Mockito.times(1)).update(film);
    }

    @Test
    void test5_shouldDeleteFilmById() {
        filmService.deleteById(1L);

        Mockito.verify(filmStorage, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void test6_shouldAddLike() throws NoSuchFieldException, IllegalAccessException {
        filmStorage = new InMemoryFilmStorage();
        likeStorage = new InMemoryLikeStorage();
        filmService = new FilmServiceImpl(filmStorage, likeStorage);

        Film film = new Film().toBuilder()
                .id(1L)
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        Field field = filmStorage.getClass().getDeclaredField("films");
        field.setAccessible(true);
        Map<Long, Film> films = (HashMap<Long, Film>) field.get(filmStorage);

        films.put(film.getId(), film);

        field = likeStorage.getClass().getDeclaredField("likes");
        field.setAccessible(true);
        Map<Long, Set<Long>> likes = (HashMap<Long, Set<Long>>) field.get(likeStorage);

        likes.put(film.getId(), new HashSet<>());

        filmService.addLike(1L, 1L);

        assertEquals(Set.of(1L), new HashSet<>(likes.get(1L)));
    }

    @Test
    void test7_shouldThrowExceptionIfTryDoAddLikeUnknownFilm() throws NoSuchFieldException, IllegalAccessException {
        filmStorage = new InMemoryFilmStorage();
        likeStorage = new InMemoryLikeStorage();
        filmService = new FilmServiceImpl(filmStorage, likeStorage);

        Film film = new Film().toBuilder()
                .id(1L)
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        Field field = filmStorage.getClass().getDeclaredField("films");
        field.setAccessible(true);
        Map<Long, Film> films = (HashMap<Long, Film>) field.get(filmStorage);

        films.put(film.getId(), film);

        field = likeStorage.getClass().getDeclaredField("likes");
        field.setAccessible(true);
        Map<Long, Set<Long>> likes = (HashMap<Long, Set<Long>>) field.get(likeStorage);

        likes.put(film.getId(), new HashSet<>());

        Exception exception = assertThrows(NotFoundException.class, () -> filmService.addLike(2L, 1L));
        String expectedMessage = "Фильм с id = 2 не найден.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test8_shouldDeleteLikeByFilmIdAndUserId() throws NoSuchFieldException, IllegalAccessException {
        filmStorage = new InMemoryFilmStorage();
        likeStorage = new InMemoryLikeStorage();
        filmService = new FilmServiceImpl(filmStorage, likeStorage);

        Film film = new Film().toBuilder()
                .id(1L)
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        Field field = filmStorage.getClass().getDeclaredField("films");
        field.setAccessible(true);
        Map<Long, Film> films = (HashMap<Long, Film>) field.get(filmStorage);

        films.put(film.getId(), film);

        field = likeStorage.getClass().getDeclaredField("likes");
        field.setAccessible(true);
        Map<Long, Set<Long>> likes = (HashMap<Long, Set<Long>>) field.get(likeStorage);

        likes.put(film.getId(), new HashSet<>());
        likes.get(film.getId()).add(1L);

        assertEquals(Set.of(1L), new HashSet<>(likes.get(1L)));

        filmService.deleteLike(film.getId(), 1L);

        assertTrue(likes.get(film.getId()).isEmpty());
    }

    @Test
    void test9_shouldThrowExceptionIfTryDoDeleteLikeUnknownFilm() throws NoSuchFieldException, IllegalAccessException {
        filmStorage = new InMemoryFilmStorage();
        likeStorage = new InMemoryLikeStorage();
        filmService = new FilmServiceImpl(filmStorage, likeStorage);

        Film film = new Film().toBuilder()
                .id(1L)
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        Field field = filmStorage.getClass().getDeclaredField("films");
        field.setAccessible(true);
        Map<Long, Film> films = (HashMap<Long, Film>) field.get(filmStorage);

        films.put(film.getId(), film);

        field = likeStorage.getClass().getDeclaredField("likes");
        field.setAccessible(true);
        Map<Long, Set<Long>> likes = (HashMap<Long, Set<Long>>) field.get(likeStorage);

        likes.put(film.getId(), new HashSet<>());

        Exception exception = assertThrows(NotFoundException.class, () -> filmService.deleteLike(2L, 1L));
        String expectedMessage = "Фильм с id = 2 не найден.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test10_shouldReturnPopularFilms() {
        filmService.findPopular(10);

        Mockito.verify(likeStorage, Mockito.times(1)).findPopular(10);
    }
}