package ru.yandex.practicum.filmorate.service.friendship;

import java.util.List;

public interface FriendshipService {
    void addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);

    List<Long> getFriendsIds(long userId);
}
