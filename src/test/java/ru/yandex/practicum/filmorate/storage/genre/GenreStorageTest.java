package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GenreStorageTest {
    private final GenreDbStorage genreStorage;
    private final FilmDbStorage filmStorage;

    @Test
    public void test1_shouldReturnGenreById() {
        Optional<Genre> genreOptional = genreStorage.findById(1);

        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre -> {
                    assertThat(genre).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(genre).hasFieldOrPropertyWithValue("name", "Комедия");
                });
    }

    @Test
    public void test2_shouldReturnAllGenres() {
        List<Genre> genres = genreStorage.findAll();

        assertEquals(6, genres.size());
    }

    @Test
    public void test3_shouldReturnGenresByIds() {
        List<Genre> genres = genreStorage.findAllById(List.of(1, 2, 3));

        assertEquals(3, genres.size());
        assertThat(genres.get(0)).hasFieldOrPropertyWithValue("id", 1);
        assertThat(genres.get(0)).hasFieldOrPropertyWithValue("name", "Комедия");
        assertThat(genres.get(1)).hasFieldOrPropertyWithValue("id", 2);
        assertThat(genres.get(1)).hasFieldOrPropertyWithValue("name", "Драма");
        assertThat(genres.get(2)).hasFieldOrPropertyWithValue("id", 3);
        assertThat(genres.get(2)).hasFieldOrPropertyWithValue("name", "Мультфильм");
    }

    @Test
    public void test4_shouldReturnGenresByFilm() {
        Film newFilm = new Film().toBuilder()
                .name("New Film")
                .description("New Film")
                .releaseDate(LocalDate.now())
                .mpaId(1)
                .duration(200)
                .genres(List.of(
                        new Genre(1, "Комедия"),
                        new Genre(2, "Драма"),
                        new Genre(4, "Триллер")))
                .build();

        filmStorage.save(newFilm);

        List<Genre> genres = genreStorage.findAllByFilm(newFilm);

        assertEquals(3, genres.size());
        assertThat(genres.get(0)).hasFieldOrPropertyWithValue("id", 1);
        assertThat(genres.get(0)).hasFieldOrPropertyWithValue("name", "Комедия");
        assertThat(genres.get(1)).hasFieldOrPropertyWithValue("id", 2);
        assertThat(genres.get(1)).hasFieldOrPropertyWithValue("name", "Драма");
        assertThat(genres.get(2)).hasFieldOrPropertyWithValue("id", 4);
        assertThat(genres.get(2)).hasFieldOrPropertyWithValue("name", "Триллер");
    }
}
