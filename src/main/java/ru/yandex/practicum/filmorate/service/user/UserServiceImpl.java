package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements ru.yandex.practicum.filmorate.service.user.UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    public User create(User user) {
        log.info("Получен запрос на создание нового пользователя: {}.", user);

        return userStorage.create(user);
    }

    public Collection<User> findAll() {
        log.info("Получен запрос на поиск всех пользователей.");

        return userStorage.findAll();
    }

    public User findById(long userId) {
        log.info("Получен запрос на поиск пользователя с id = {}.", userId);

        return userStorage.findById(userId);
    }

    public User update(User user) {
        log.info("Получен запрос на обновление пользователя с id = {}. Новое значение: {}", user.getId(), user);

        return userStorage.update(user);
    }

    public void deleteById(long userId) {
        log.info("Получен запрос на удаление пользователя с id = {}.", userId);

        userStorage.deleteById(userId);
    }

    public void addFriend(long userId, long friendId) {
        log.info("Получен запрос на добавление в друзья. " +
                "Пользователь с id = {} хочет добавить в друзья пользователя с id = {}", userId, friendId);

        friendStorage.addFriend(userStorage.findById(userId), userStorage.findById(friendId));
    }

    public void deleteFriend(long userId, long friendId) {
        log.info("Получен запрос на удаление из друзей. " +
                "Пользователь с id = {} хочет удалить из друзей пользователя с id = {}", userId, friendId);

        friendStorage.deleteFriend(userStorage.findById(userId), userStorage.findById(friendId));
    }

    public Collection<User> findFriends(long userId) {
        log.info("Получен запрос на поиск друзей пользователя с id = {}.", userId);

        return friendStorage.findFriends(userId);
    }

    public Collection<User> findCommonFriends(long userId, long otherId) {
        log.info("Получен запрос на поиск общих друзей пользователя с id = {} и пользователя с id = {}.", userId, otherId);

        return friendStorage.findFriends(userId).stream()
                .filter(user -> findFriends(otherId).contains(user))
                .collect(Collectors.toList());
    }
}