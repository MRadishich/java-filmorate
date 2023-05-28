package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.friendship.Friendship;

import java.util.List;

public interface FriendDbStorage {
    List<Friendship> findAllByUserId(Long userId);

    boolean existsById(Friendship friendship);

    void save(Friendship friendship);

    void delete(Friendship friendship);
}
