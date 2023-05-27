package ru.yandex.practicum.filmorate.service.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.function.Function;

@Component
class UserFromDTOMapper implements Function<UserDTO, User> {
    @Override
    public User apply(UserDTO user) {
        return new User(
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName(),
                user.getBirthday()
        );
    }
}
