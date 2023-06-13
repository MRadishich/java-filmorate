package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.enums.FriendshipStatus;

import java.util.List;

public interface FriendStorage {
    List<Long> findAllFriendIdsByUserId(long userId);

    boolean existsById(long userId, long friendId);

    void save(long userId, long friendId, FriendshipStatus status);

    void delete(long userId, long friendId);
}
