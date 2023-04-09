package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmTest {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    @Test
    public void test1_shouldCreateFilmWithoutViolations() {
        Film film = Film.builder()
                .id(1)
                .name("The best movie")
                .description("The best movie of all time")
                .duration(200)
                .releaseDate(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void test2_shouldNotCreateFilmIfNameIsEmpty() {
        Film film = Film.builder()
                .id(1)
                .name("")
                .description("The best movie of all time")
                .duration(200)
                .releaseDate(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Название фильма не может быть пустым.", message);
    }

    @Test
    public void test3_shouldNotCreateFilmIfNameIsBlank() {
        Film film = Film.builder()
                .id(1)
                .name("  ")
                .description("The best movie of all time")
                .duration(200)
                .releaseDate(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Название фильма не может быть пустым.", message);
    }

    @Test
    public void test4_shouldNotCreateFilmIfNameIsNull() {
        Film film = Film.builder()
                .id(1)
                .name(null)
                .description("The best movie of all time")
                .duration(200)
                .releaseDate(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Название фильма не может быть пустым.", message);
    }

    @Test
    public void test5_shouldNotCreateFilmIfDescriptionLengthMore200() {
        Film film = new Film().toBuilder()
                .id(1)
                .name("The best film")
                .description("a".repeat(Film.DESCRIPTION_MAX_LENGTH) + 1)
                .duration(200)
                .releaseDate(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Максимальная длина описания — " + Film.DESCRIPTION_MAX_LENGTH + " символов.", message);
    }

    @Test
    public void test6_shouldCreateFilmIfDescriptionLengthEquals200() {
        Film film = new Film().toBuilder()
                .id(1)
                .name("The best film")
                .description("a".repeat(Film.DESCRIPTION_MAX_LENGTH))
                .duration(200)
                .releaseDate(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void test7_shouldNotCreateFilmIfDateReleaseBeforeMinDateRelease() {
        Film film = new Film().toBuilder()
                .id(1)
                .name("The best film")
                .description("The best movie of all time")
                .duration(200)
                .releaseDate(LocalDate.parse(Film.MIN_DATE_RELEASE).minusDays(1))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Дата релиза не может быть раньше " + Film.MIN_DATE_RELEASE + ".", message);
    }

    @Test
    public void test8_shouldCreateFilmIfDateReleaseEqualsMinDateRelease() {
        Film film = new Film().toBuilder()
                .id(1)
                .name("The best film")
                .description("The best movie of all time")
                .duration(200)
                .releaseDate(LocalDate.parse(Film.MIN_DATE_RELEASE))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void test9_shouldNotCreateFilmIfDurationIsNegative() {
        Film film = new Film().toBuilder()
                .id(1)
                .name("The best film")
                .description("The best movie of all time")
                .duration(-10)
                .releaseDate(LocalDate.parse(Film.MIN_DATE_RELEASE))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Продолжительность фильма не может быть меньше 0.", message);
    }

    @Test
    public void test10_shouldNotCreateFilmIfDurationIsNull() {
        Film film = new Film().toBuilder()
                .id(1)
                .name("The best film")
                .description("The best movie of all time")
                .duration(null)
                .releaseDate(LocalDate.parse(Film.MIN_DATE_RELEASE))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Продолжительность фильма - обязательное поле.", message);
    }
}
