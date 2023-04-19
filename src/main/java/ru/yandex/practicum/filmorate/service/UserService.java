package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public User findById(long userId) {
        log.info("Получен запрос на поиск пользователя с id = {}.", userId);

        return storage.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден.", userId)));
    }

    public User update(User user) {
        log.info("Получен запрос на обновление пользователя с id = {}. Новое значение: {}", user.getId(), user);

        return storage.update(user);
    }

    public void deleteById(long userId) {
        log.info("Получен запрос на удаление пользователя с id = {}.", userId);

        storage.deleteById(userId);
    }

    public void addFriend(long userId, long friendId) {
        log.info("Получен запрос на добавление в друзья. " +
                "Пользователь с id = {} хочет добавить в друзья пользователя с id = {}", userId, friendId);

        Optional<User> user = storage.findById(userId);
        Optional<User> friend = storage.findById(friendId);

        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден.", userId));
        }

        if (friend.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден.", friendId));
        }

        user.get().addFriend(friendId);
        friend.get().addFriend(userId);
    }

    public void deleteFriend(long userId, long friendId) {
        log.info("Получен запрос на удаление из друзей. " +
                "Пользователь с id = {} хочет удалить из друзей пользователя с id = {}", userId, friendId);

        Optional<User> user = storage.findById(userId);
        Optional<User> friend = storage.findById(friendId);

        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден.", userId));
        }

        if (friend.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден.", friendId));
        }

        user.get().deleteFriend(friendId);
        friend.get().deleteFriend(userId);
    }

    public Collection<User> findFriends(long userId) {
        log.info("Получен запрос на поиск друзей пользователя с id = {}.", userId);

        return storage.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден.", userId)))
                .getFriends()
                .stream()
                .map(id -> storage.findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format("Друг с id = %d не найден.", id))))
                .collect(Collectors.toList());
    }

    public Collection<User> findCommonFriends(long userId, long otherId) {
        log.info("Получен запрос на поиск общих друзей пользователя с id = {} и пользователя с id = {}.", userId, otherId);

        return storage.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден.", userId)))
                .getFriends().stream()
                .filter(u -> storage.findById(otherId)
                        .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден.", otherId)))
                        .getFriends()
                        .contains(u))
                .map(id -> storage.findById(id).orElseThrow())
                .collect(Collectors.toList());
    }
}