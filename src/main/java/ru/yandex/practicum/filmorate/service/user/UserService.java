package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);

    List<UserDto> getAllUsers();

    UserDto getUserById(long id);

    UserDto updateUser(UserDto user);

    void deleteUserById(long id);

    void addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);

    List<UserDto> getFriends(long userId);

    List<UserDto> getCommonFriends(long userId, long otherId);

}
