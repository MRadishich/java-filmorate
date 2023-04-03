package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    @Email(message = "Некорректный адрес электронной почты.")
    private String email;
    @Pattern(regexp = "^\\S+$", message = "Логин не может быть пустым и содержать пробелы.")
    private final String login;
    private String name;
    @PastOrPresent(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;
}
