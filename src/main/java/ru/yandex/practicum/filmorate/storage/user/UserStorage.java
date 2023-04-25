package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    User create(User user);

    Collection<User> findAll();

    Optional<User> findById(long userId);

    User update(User user);

    void deleteById(long userId);
}
