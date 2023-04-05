package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
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


    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем.")
    private final LocalDate birthday;
}
