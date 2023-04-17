package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage storage;

    public User create(User user) {
        return storage.create(user);
    }

    public Collection<User> findAll() {
        return storage.findAll();
    }

    public User findById(long id) {
        return storage.findById(id);
    }

    public User update(User user) {
        return storage.update(user);
    }

    public void deleteById(long id) {
        storage.deleteById(id);
    }
}
