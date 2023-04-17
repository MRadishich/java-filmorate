package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User create(User user);

    Collection<User> findAll();

    User findById(long id);

    User update(User user);

    void deleteById(long id);
}
