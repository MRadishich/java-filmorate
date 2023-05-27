package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.dto.UserDTO;

import java.util.Collection;

public interface UserService {
    UserDTO createUser(UserDTO user);

    Collection<UserDTO> findAllUsers();

    UserDTO findUserById(Long id);

    UserDTO updateUser(UserDTO user);

    void deleteUserById(long id);

    void addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);

    Collection<UserDTO> findFriends(long userId);

    Collection<UserDTO> findCommonFriends(long userId, long otherId);

}
