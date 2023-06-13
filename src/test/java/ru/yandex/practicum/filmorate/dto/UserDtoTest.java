package ru.yandex.practicum.filmorate.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    @Test
    public void test1_shouldCreateUserDTO() {
        // Given
        UserDto user = new UserDto(
                1L,
                "email@email.com",
                "SuperUser",
                "Super Name",
                LocalDate.parse("2020-02-02")
        );

        // When
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void test2_shouldNotCreateUserDTOIfEmailIsInvalid() {
        // Given
        String[] invalidEmails = {
                "email_email",
                "@fds.com",
                "email @ email",
                "email @email.com",
                null,
                "",
                "  "
        };

        Arrays.stream(invalidEmails).forEach(email -> {
            UserDto user = new UserDto(
                    1L,
                    email,
                    "SuperUser",
                    "Super Name",
                    LocalDate.parse("2020-02-02")
            );

            // When
            Set<ConstraintViolation<UserDto>> violations = validator.validate(user);

            // Then
            assertFalse(violations.isEmpty());
        });
    }

    @Test
    public void test3_shouldNotCreateUserDTOIfLoginIsEmpty() {
        // Given
        UserDto user = new UserDto(
                1L,
                "email@email.com",
                "",
                "Super Name",
                LocalDate.parse("2020-02-02")
        );

        // When
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
    }

    @Test
    public void test4_shouldNotCreateUserDTOIfLoginIsBlank() {
        // Given
        UserDto user = new UserDto(
                1L,
                "email@email.com",
                " ",
                "Super Name",
                LocalDate.parse("2020-02-02")
        );

        // When
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
    }

    @Test
    public void test5_shouldNotCreateUserDTOIfLoginIsNull() {
        // Given
        UserDto user = new UserDto(
                1L,
                "email@email.com",
                null,
                "Super Name",
                LocalDate.parse("2020-02-02")
        );

        // When
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
    }

    @Test
    public void test6_shouldNotCreateUserDTOIfLoginHasWhitespace() {
        // Given
        UserDto user = new UserDto(
                1L,
                "email@email.com",
                "Super User",
                "Super Name",
                LocalDate.parse("2020-02-02")
        );

        // When
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
    }

    @Test
    public void test7_shouldCreateUserDTOIfNameIsNull() {
        // Given
        UserDto user = new UserDto(
                1L,
                "email@email.com",
                "SuperUser",
                null,
                LocalDate.parse("2020-02-02")
        );

        // When
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void test8_shouldNotCreateUserDTOIfBirthDayInFuture() {
        // Given
        UserDto user = new UserDto(
                1L,
                "email@email.com",
                "SuperUser",
                "Super Name",
                LocalDate.now().plusDays(1)
        );

        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);

        // When
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        // Then
        assertEquals("Дата рождения не может быть в будущем.", message);
    }
}
