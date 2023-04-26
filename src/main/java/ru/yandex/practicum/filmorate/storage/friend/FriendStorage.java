package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;

public interface FriendStorage {
    Collection<User> findFriends(long userId);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);
}
