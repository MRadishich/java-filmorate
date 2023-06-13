package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

class UserMapper {

    public static User toUser(UserDto user) {
        return new User(
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName(),
                user.getBirthday()
        );
    }

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
    }
}
