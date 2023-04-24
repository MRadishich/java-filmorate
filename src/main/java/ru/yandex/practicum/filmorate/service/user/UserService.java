package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    User create(User user);

    Collection<User> findAll();

    User findById(long id);

    User update(User user);

    void deleteById(long id);

    void addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);

    Collection<User> findFriends(long userId);

    Collection<User> findCommonFriends(long userId, long otherId);

}
