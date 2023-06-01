package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);

    List<UserDTO> findAllUsers();

    UserDTO findUserById(Long id);

    UserDTO updateUser(UserDTO user);

    void deleteUserById(long id);

    void addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);

    List<UserDTO> findFriends(long userId);

    List<UserDTO> findCommonFriends(long userId, long otherId);

}
