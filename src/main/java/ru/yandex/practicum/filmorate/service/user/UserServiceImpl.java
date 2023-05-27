package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.friendship.FriendshipService;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDbStorage userStorage;
    private final UserToDTOMapper userToDTO;
    private final UserFromDTOMapper userFromDTO;
    private final FriendshipService friendshipService;

    public UserDTO createUser(UserDTO userDTO) {
        log.info("Получен запрос на создание нового пользователя: {}.", userDTO);

        User user = userFromDTO.apply(userDTO);

        return userToDTO.apply(userStorage.save(user));
    }

    public Collection<UserDTO> findAllUsers() {
        log.info("Получен запрос на поиск всех пользователей.");

        return userStorage.findAll().stream()
                .map(userToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(Long userId) {
        log.info("Получен запрос на поиск пользователя с id = {}.", userId);

        return userStorage.findById(userId)
                .map(userToDTO)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден."));
    }

    public UserDTO updateUser(UserDTO userDTO) {
        Long userId = userDTO.getId();

        log.info("Получен запрос на обновление пользователя с id = {}. Новое значение: {}", userId, userDTO);

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        User user = userFromDTO.apply(userDTO);

        return userToDTO.apply(userStorage.save(user));
    }

    public void deleteUserById(long userId) {
        log.info("Получен запрос на удаление пользователя с id = {}.", userId);

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        userStorage.deleteById(userId);
    }

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

    public Collection<UserDTO> findFriends(long userId) {
        log.info("Получен запрос на поиск друзей пользователя с id = {}.", userId);

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        return userStorage.findAllById(friendshipService.findFriendsIds(userId))
                .stream()
                .map(userToDTO)
                .collect(Collectors.toList());
    }

    public Collection<UserDTO> findCommonFriends(long userId, long otherId) {
        log.info("Получен запрос на поиск общих друзей пользователя с id = {} и пользователя с id = {}.", userId, otherId);

        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден.");
        }

        if (!userStorage.existsById(otherId)) {
            throw new NotFoundException("Пользователь с id = " + otherId + " не найден.");
        }

        Collection<UserDTO> friends = findFriends(userId);
        Collection<UserDTO> otherFriend = findFriends(otherId);
        friends.retainAll(otherFriend);

        return friends;
    }
}
