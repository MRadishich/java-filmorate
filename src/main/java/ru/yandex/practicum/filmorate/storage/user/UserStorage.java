package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;
import java.util.Optional;


public interface UserStorage {
    User save(User user);

    List<User> findAll();

    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);

    boolean existsById(long userId);

    void deleteById(long userId);

    List<User> findAllById(List<Long> ids);

}
