package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserRepository {
    int create(User user);

    Collection<User> getAll();

    User get(int id);

    boolean update(int id, User user);

    boolean delete(int id);
}
