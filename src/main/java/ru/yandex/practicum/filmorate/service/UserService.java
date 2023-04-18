package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage storage;

    public User create(User user) {
        log.info("Получен запрос на создание нового пользователя: {}.", user);

        return storage.create(user);
    }

    public Collection<User> findAll() {
        log.info("Получен запрос на поиск всех пользователей.");

        return storage.findAll();
    }

    public User findById(long id) {
        log.info("Получен запрос на поиск пользователя с id = {}.", id);

        return storage.findById(id).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %s не найден.", id)));
    }

    public User update(User user) {
        log.info("Получен запрос на обновление пользователя с id = {}. Новое значение: {}", user.getId(), user);

        return storage.update(user);
    }

    public void deleteById(long id) {
        log.info("Получен запрос на удаление пользователя с id = {}.", id);

        storage.deleteById(id);
    }
}
