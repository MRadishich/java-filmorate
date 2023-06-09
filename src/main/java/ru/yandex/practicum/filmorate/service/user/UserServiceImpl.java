package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.friendship.FriendshipService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final FriendshipService friendshipService;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDTO) {
        log.info("Получен запрос на создание нового пользователя: {}.", userDTO);

        User user = UserMapper.toUser(userDTO);

        if (userStorage.findByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Пользователь с email = " + user.getEmail() + " уже существует.");
        }

        return UserMapper.toDto(userStorage.save(user));
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        log.info("Получен запрос на поиск всех пользователей.");

        return userStorage.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto getUserById(long userId) {
        log.info("Получен запрос на поиск пользователя с id = {}.", userId);

        return userStorage.findById(userId)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден."));
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDTO) {
        Long userId = userDTO.getId();

        log.info("Получен запрос на обновление пользователя с id = {}. Новое значение: {}", userId, userDTO);

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        User user = UserMapper.toUser(userDTO);

        return UserMapper.toDto(userStorage.save(user));
    }

    @Override
    @Transactional
    public void deleteUserById(long userId) {
        log.info("Получен запрос на удаление пользователя с id = {}.", userId);

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        userStorage.deleteById(userId);
    }

    @Override
    @Transactional
    public void addFriend(long userId, long friendId) {
        log.info("Получен запрос на добавление в друзья. " +
                "Пользователь с id = {} хочет добавить в друзья пользователя с id = {}", userId, friendId);

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        if (!userStorage.existsById(friendId)) {
            throw new NotFoundException("Пользователь с id = " + friendId + " не найден.");
        }

        friendshipService.addFriend(userId, friendId);
    }

    @Override
    @Transactional
    public void deleteFriend(long userId, long friendId) {
        log.info("Получен запрос на удаление из друзей. " +
                "Пользователь с id = {} хочет удалить из друзей пользователя с id = {}", userId, friendId);

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        if (!userStorage.existsById(friendId)) {
            throw new NotFoundException("Пользователь с id = " + friendId + " не найден.");
        }

        friendshipService.deleteFriend(userId, friendId);
    }

    @Override
    @Transactional
    public List<UserDto> getFriends(long userId) {
        log.info("Получен запрос на поиск друзей пользователя с id = {}.", userId);

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        return userStorage.findAllById(friendshipService.getFriendsIds(userId))
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UserDto> getCommonFriends(long userId, long otherId) {
        log.info("Получен запрос на поиск общих друзей пользователя с id = {} и пользователя с id = {}.", userId, otherId);

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        if (!userStorage.existsById(otherId)) {
            throw new NotFoundException("Пользователь с id = " + otherId + " не найден.");
        }

        List<UserDto> friends = getFriends(userId);
        List<UserDto> otherFriend = getFriends(otherId);
        friends.retainAll(otherFriend);

        return friends;
    }
}
