package ru.yandex.practicum.filmorate.model;

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

public class UserTest {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    @Test
    public void test1_shouldCreateUser() {
        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.com")
                .login("SuperUser")
                .name("Super Name")
                .birthday(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void test2_shouldNotCreateUserIfEmailIsInvalid() {
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
            User user = new User()
                    .toBuilder()
                    .id(1L)
                    .email(email)
                    .login("SuperUser")
                    .name("Super Name")
                    .birthday(LocalDate.parse("2020-02-02"))
                    .build();

            Set<ConstraintViolation<User>> violations = validator.validate(user);
            assertFalse(violations.isEmpty());

        });
    }

    @Test
    public void test3_shouldNotCreateUserIfLoginIsEmpty() {
        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.com")
                .login("")
                .name("Super Name")
                .birthday(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
    }

    @Test
    public void test4_shouldNotCreateUserIfLoginIsBlank() {
        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.com")
                .login("  ")
                .name("Super Name")
                .birthday(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
    }

    @Test
    public void test5_shouldNotCreateUserIfLoginIsNull() {
        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.com")
                .login(null)
                .name("Super Name")
                .birthday(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
    }

    @Test
    public void test6_shouldNotCreateUserIfLoginHasWhitespace() {
        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.com")
                .login("Super User")
                .name("Super Name")
                .birthday(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
    }

    @Test
    public void test7_shouldCreateUserIfNameIsEmpty() {
        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.com")
                .login("SuperUser")
                .birthday(LocalDate.parse("2020-02-02"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void test8_shouldNotCreateUserIfBirthDayInFuture() {
        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.com")
                .login("SuperUser")
                .name("Super Name")
                .birthday(LocalDate.now().plusDays(1))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));

        assertEquals("Дата рождения не может быть в будущем.", message);
    }
}
