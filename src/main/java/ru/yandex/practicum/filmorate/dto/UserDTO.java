package ru.yandex.practicum.filmorate.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UserDTO {
    private final Long id;

    @Email(message = "Некорректный адрес электронной почты.")
    @NotBlank(message = "Адрес электронной почти - обязательное поле.")
    private final String email;

    @NotNull(message = "Логин не может быть пустым и содержать пробелы.")
    @Pattern(regexp = "^\\S+$", message = "Логин не может быть пустым и содержать пробелы.")
    private final String login;

    private final String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем.")
    private final LocalDate birthday;
}
