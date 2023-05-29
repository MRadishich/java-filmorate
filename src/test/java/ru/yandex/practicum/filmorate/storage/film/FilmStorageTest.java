package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmStorageTest {
    private final FilmStorage filmStorage;
    private Film newFilm;

    @BeforeEach
    void setUp() {
        newFilm = new Film().toBuilder()
                .name("New Film")
                .description("New Film")
                .releaseDate(LocalDate.now())
                .mpa(new Mpa(1, "G", "У фильма нет возрастных ограничений"))
                .duration(200)
                .genres(List.of(new Genre(1, "Комедия")))
                .build();
    }

    @Test
    public void test1_shouldSaveFilm() {
        Film savedFilm = filmStorage.save(newFilm);

        assertThat(savedFilm).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(savedFilm).hasFieldOrPropertyWithValue("name", "New Film");
        assertThat(savedFilm).hasFieldOrPropertyWithValue("description", "New Film");
        assertThat(savedFilm).hasFieldOrPropertyWithValue("releaseDate", LocalDate.now());
        assertThat(savedFilm).hasFieldOrPropertyWithValue("mpa", new Mpa(1, "G", "У фильма нет возрастных ограничений"));
        assertThat(savedFilm).hasFieldOrPropertyWithValue("duration", 200);
        assertThat(savedFilm).hasFieldOrPropertyWithValue("genres", List.of(new Genre(1, "Комедия")));
    }

    @Test
    public void test2_shouldUpdateFilm() {
        filmStorage.save(newFilm);

        newFilm = new Film().toBuilder()
                .id(1L)
                .name("Update Film")
                .description("Update Film")
                .releaseDate(LocalDate.now())
                .mpa(new Mpa(1, "G", "У фильма нет возрастных ограничений"))
                .duration(200)
                .genres(List.of(new Genre(2, "Драма")))
                .build();

        Film updatedFilm = filmStorage.save(newFilm);

        assertThat(updatedFilm).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(updatedFilm).hasFieldOrPropertyWithValue("name", "Update Film");
        assertThat(updatedFilm).hasFieldOrPropertyWithValue("description", "Update Film");
        assertThat(updatedFilm).hasFieldOrPropertyWithValue("releaseDate", LocalDate.now());
        assertThat(updatedFilm).hasFieldOrPropertyWithValue("mpa", new Mpa(1, "G", "У фильма нет возрастных ограничений"));
        assertThat(updatedFilm).hasFieldOrPropertyWithValue("duration", 200);
        assertThat(updatedFilm).hasFieldOrPropertyWithValue("genres", List.of(new Genre(2, "Драма")));
    }

    @Test
    public void test3_shouldReturnFilmById() {
        filmStorage.save(newFilm);

        Optional<Film> foundFilm = filmStorage.findById(1L);

        assertThat(foundFilm)
                .isPresent()
                .hasValueSatisfying(film -> {
                    assertThat(film).hasFieldOrPropertyWithValue("id", 1L);
                    assertThat(film).hasFieldOrPropertyWithValue("name", "New Film");
                    assertThat(film).hasFieldOrPropertyWithValue("description", "New Film");
                    assertThat(film).hasFieldOrPropertyWithValue("releaseDate", LocalDate.now());
                    assertThat(film).hasFieldOrPropertyWithValue("mpa", new Mpa(1, "G", "У фильма нет возрастных ограничений"));
                    assertThat(film).hasFieldOrPropertyWithValue("duration", 200);
                    assertThat(film).hasFieldOrPropertyWithValue("genres", List.of(new Genre(1, "Комедия")));
                });
    }

    @Test
    public void test4_shouldReturnAllFilm() {
        filmStorage.save(newFilm);

        List<Film> films = filmStorage.findAll();

        assertEquals(1, films.size());
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("name", "New Film");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("description", "New Film");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("releaseDate", LocalDate.now());
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("mpa", new Mpa(1, "G", "У фильма нет возрастных ограничений"));
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("duration", 200);
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("genres", List.of(new Genre(1, "Комедия")));

        Film otherFilm = new Film().toBuilder()
                .name("Other Film")
                .description("Other Film")
                .releaseDate(LocalDate.now())
                .mpa(new Mpa(1, "G", "У фильма нет возрастных ограничений"))
                .duration(200)
                .genres(List.of(new Genre(2, "Боевик")))
                .build();

        filmStorage.save(otherFilm);

        films = filmStorage.findAll();

        assertEquals(2, films.size());
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("name", "New Film");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("description", "New Film");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("releaseDate", LocalDate.now());
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("mpa", new Mpa(1, "G", "У фильма нет возрастных ограничений"));
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("duration", 200);
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("genres", List.of(new Genre(1, "Комедия")));
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("name", "Other Film");
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("description", "Other Film");
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("releaseDate", LocalDate.now());
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("mpa", new Mpa(1, "G", "У фильма нет возрастных ограничений"));
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("duration", 200);
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("genres", List.of(new Genre(2, "Драма")));
    }

    @Test
    public void test5_shouldReturnFilmsByIds() {
        filmStorage.save(newFilm);

        Film otherFilm = new Film().toBuilder()
                .name("Other Film")
                .description("Other Film")
                .releaseDate(LocalDate.now())
                .mpa(new Mpa(1, "G", "У фильма нет возрастных ограничений"))
                .duration(200)
                .genres(List.of(new Genre(2, "Боевик")))
                .build();

        filmStorage.save(otherFilm);

        Film otherFilmTwo = new Film().toBuilder()
                .name("Other Film Two")
                .description("Other Film Two")
                .releaseDate(LocalDate.now())
                .mpa(new Mpa(1, "G", "У фильма нет возрастных ограничений"))
                .duration(200)
                .genres(List.of(new Genre(2, "Боевик")))
                .build();

        filmStorage.save(otherFilmTwo);

        List<Film> films = filmStorage.findAllById(List.of(1L, 2L));

        assertEquals(2, films.size());
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("name", "New Film");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("description", "New Film");
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("releaseDate", LocalDate.now());
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("mpa", new Mpa(1, "G", "У фильма нет возрастных ограничений"));
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("duration", 200);
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("genres", List.of(new Genre(1, "Комедия")));
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("name", "Other Film");
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("description", "Other Film");
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("releaseDate", LocalDate.now());
        assertThat(films.get(0)).hasFieldOrPropertyWithValue("mpa", new Mpa(1, "G", "У фильма нет возрастных ограничений"));
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("duration", 200);
        assertThat(films.get(1)).hasFieldOrPropertyWithValue("genres", List.of(new Genre(2, "Драма")));
    }

    @Test
    public void test6_shouldReturnTrueIfUserExists() {
        filmStorage.save(newFilm);

        assertTrue(filmStorage.existsById(1L));
    }

    @Test
    public void test7_shouldReturnFalseIfUserExists() {

        assertFalse(filmStorage.existsById(1L));
    }
}
