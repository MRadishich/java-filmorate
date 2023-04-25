package ru.yandex.practicum.filmorate.storage.friends;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;

public interface FriendsStorage {
    void createFriendList(long userId);
    Collection<User> findFriends(long userId);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);
}
