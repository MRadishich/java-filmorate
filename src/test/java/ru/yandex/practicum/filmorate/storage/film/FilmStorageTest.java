package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FilmStorageTest {
    FilmStorage filmStorage;

    @BeforeEach
    void setUp() {
        filmStorage = new InMemoryFilmStorage();
    }

    @Test
    void test1_shouldSaveNewFilmInStorage() throws NoSuchFieldException, IllegalAccessException {
        Film film = new Film().toBuilder()
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmStorage.create(film);

        Field field = filmStorage.getClass().getDeclaredField("films");

        field.setAccessible(true);

        Map<Long, Film> map = (HashMap<Long, Film>) field.get(filmStorage);

        assertEquals(film, map.get(1L));
    }

    @Test
    void test2_shouldReturnAllFilmFromStorage() {
        Film film1 = new Film().toBuilder()
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        Film film2 = new Film().toBuilder()
                .name("New the best film")
                .description("New the best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmStorage.create(film1);
        filmStorage.create(film2);

        Collection<Film> films = filmStorage.findAll();

        assertEquals(2, films.size());
        assertTrue(films.contains(film1));
        assertTrue(films.contains(film2));
    }

    @Test
    void test3_shouldReturnFilmById() {
        Film film1 = new Film().toBuilder()
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmStorage.create(film1);

        Film film2 = filmStorage.findById(1L);

        assertEquals("The best film", film2.getName());
        assertEquals("The best film in your life.", film2.getDescription());
        assertEquals(LocalDate.now(), film2.getReleaseDate());
        assertEquals(150, film2.getDuration());
    }

    @Test
    void test4_shouldThrowExceptionIfUpdateUnknownFilm() {
        Film film1 = new Film().toBuilder()
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmStorage.create(film1);

        Exception exception = assertThrows(NotFoundException.class, () -> filmStorage.findById(2L));
        String expectedMessage = "Фильм с id = 2 не найден.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test5_shouldReturnCollectionFilmsByIds() {
        Film film1 = new Film().toBuilder()
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        Film film2 = new Film().toBuilder()
                .name("New the best film")
                .description("New the best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmStorage.create(film1);
        filmStorage.create(film2);

        Collection<Film> films = filmStorage.findByIds(List.of(1L, 2L));

        assertEquals(2, films.size());
        assertTrue(films.contains(film1));
        assertTrue(films.contains(film2));
    }

    @Test
    void test6_shouldUpdateFilmAndReturnUpdatedFilm() {
        Film film = new Film().toBuilder()
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmStorage.create(film);

        Film updatedFilm = new Film().toBuilder()
                .id(1L)
                .name("Updated the best film")
                .description("Updated the best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmStorage.update(updatedFilm);

        Film verifaibleFilm = filmStorage.findById(1L);

        assertEquals("Updated the best film", verifaibleFilm.getName());
        assertEquals("Updated the best film in your life.", verifaibleFilm.getDescription());
        assertEquals(LocalDate.now(), verifaibleFilm.getReleaseDate());
        assertEquals(150, verifaibleFilm.getDuration());
    }

    @Test
    void test7_shouldThrowExceptionIfUpdateUnknownFilm() {
        Film film = new Film().toBuilder()
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmStorage.create(film);

        Film updatedFilm = new Film().toBuilder()
                .id(2L)
                .name("Updated the best film")
                .description("Updated the best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        Exception exception = assertThrows(NotFoundException.class, () -> filmStorage.update(updatedFilm));
        String expectedMessage = "Фильм с id = 2 не найден.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test8_shouldDeleteFilmById() {
        Film film1 = new Film().toBuilder()
                .name("The best film")
                .description("The best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        Film film2 = new Film().toBuilder()
                .name("New the best film")
                .description("New the best film in your life.")
                .releaseDate(LocalDate.now())
                .duration(150)
                .build();

        filmStorage.create(film1);
        filmStorage.create(film2);

        Collection<Film> films = filmStorage.findAll();

        assertEquals(2, films.size());
        assertTrue(films.contains(film1));
        assertTrue(films.contains(film2));

        filmStorage.deleteById(1L);

        films = filmStorage.findAll();

        assertEquals(1, films.size());
        assertFalse(films.contains(film1));
        assertTrue(films.contains(film2));
    }

    @Test
    void test9_shouldThrowExceptionIfDeleteUnknownFilmById() {
        Exception exception = assertThrows(NotFoundException.class, () -> filmStorage.deleteById(1L));
        String expectedMessage = "Фильм с id = 1 не найден.";

        assertEquals(expectedMessage, exception.getMessage());
    }
}