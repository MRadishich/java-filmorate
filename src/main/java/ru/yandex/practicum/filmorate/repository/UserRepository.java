package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserRepository {
    User create(User user);

    Collection<User> getAll();

    User get(int id);

    User update(User user);

    boolean delete(int id);
}
