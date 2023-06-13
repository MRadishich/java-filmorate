package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.model.User;

class UserMapper {

    public static User toUser(UserDTO user) {
        return new User(
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName(),
                user.getBirthday()
        );
    }

    public static UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
    }
}
