package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @EqualsAndHashCode.Exclude
    private int id;

    @Email(message = "Некорректный адрес электронной почты.")
    @NotBlank(message = "Адрес электронной почти - обязательное поле.")
    private String email;

    @NotNull(message = "Логин не может быть пустым и содержать пробелы.")
    @Pattern(regexp = "^\\S+$", message = "Логин не может быть пустым и содержать пробелы.")
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;
}
