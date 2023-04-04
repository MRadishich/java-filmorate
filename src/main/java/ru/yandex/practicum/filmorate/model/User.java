package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
public class User {
    @EqualsAndHashCode.Exclude
    private int id;

    @Email(message = "Некорректный адрес электронной почты.")
    private final String email;

    @Pattern(regexp = "^\\S+$", message = "Логин не может быть пустым и содержать пробелы.")
    private final String login;

    private final String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем.")
    private final LocalDate birthday;
}
