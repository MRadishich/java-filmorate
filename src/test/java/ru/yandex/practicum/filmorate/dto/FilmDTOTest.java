package ru.yandex.practicum.filmorate.dto;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmDTOTest {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    @Test
    public void test1_shouldCreateFilmDTOWithoutViolations() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                "The best movie",
                LocalDate.parse("2020-02-02"),
                "The best movie of all time",
                200,
                new Mpa(),
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void test2_shouldNotCreateFilmDTOIfNameIsEmpty() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                "",
                LocalDate.parse("2020-02-02"),
                "The best movie of all time",
                200,
                new Mpa(),
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Название фильма не может быть пустым.", message);
    }

    @Test
    public void test3_shouldNotCreateFilmDTOIfNameIsBlank() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                " ",
                LocalDate.parse("2020-02-02"),
                "The best movie of all time",
                200,
                new Mpa(),
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Название фильма не может быть пустым.", message);
    }

    @Test
    public void test4_shouldNotCreateFilmDTOIfNameIsNull() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                null,
                LocalDate.parse("2020-02-02"),
                "The best movie of all time",
                200,
                new Mpa(),
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Название фильма не может быть пустым.", message);
    }

    @Test
    public void test5_shouldNotCreateFilmDTOIfDescriptionLengthMore200() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                "The best movie",
                LocalDate.parse("2020-02-02"),
                "a".repeat(FilmDTO.DESCRIPTION_MAX_LENGTH + 1),
                200,
                new Mpa(),
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Максимальная длина описания — " + FilmDTO.DESCRIPTION_MAX_LENGTH + " символов.", message);
    }

    @Test
    public void test6_shouldCreateFilmDTOIfDescriptionLengthEquals200() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                "The best movie",
                LocalDate.parse("2020-02-02"),
                "a".repeat(FilmDTO.DESCRIPTION_MAX_LENGTH),
                200,
                new Mpa(),
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void test7_shouldNotCreateFilmDTOIfDateReleaseBeforeMinDateRelease() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                "The best movie",
                LocalDate.parse(FilmDTO.MIN_DATE_RELEASE).minusDays(1),
                "The best movie of all time",
                200,
                new Mpa(),
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Дата релиза не может быть раньше " + FilmDTO.MIN_DATE_RELEASE + ".", message);
    }

    @Test
    public void test8_shouldCreateFilmDTOIfDateReleaseEqualsMinDateRelease() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                "The best movie",
                LocalDate.parse(FilmDTO.MIN_DATE_RELEASE),
                "The best movie of all time",
                200,
                new Mpa(),
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void test9_shouldNotCreateFilmDTOIfDurationIsNegative() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                "The best movie",
                LocalDate.parse("2020-02-02"),
                "The best movie of all time",
                -200,
                new Mpa(),
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Продолжительность фильма не может быть меньше 0.", message);
    }

    @Test
    public void test10_shouldNotCreateFilmDTOIfDurationIsNull() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                "The best movie",
                LocalDate.parse("2020-02-02"),
                "The best movie of all time",
                null,
                new Mpa(),
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Продолжительность фильма - обязательное поле.", message);
    }

    @Test
    public void test10_shouldNotCreateFilmDTOIfMpaIsNull() {
        // Given
        FilmDTO film = new FilmDTO(
                1L,
                "The best movie",
                LocalDate.parse("2020-02-02"),
                "The best movie of all time",
                200,
                null,
                null
        );

        // When
        Set<ConstraintViolation<FilmDTO>> violations = validator.validate(film);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Рейтинг Ассоциации кинокомпаний - обязательное поле.", message);
    }
}
