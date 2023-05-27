package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.filmorate.model.friendship.Friendship;
import ru.yandex.practicum.filmorate.model.friendship.FriendshipId;

import java.util.List;

public interface FriendDbStorage extends JpaRepository<Friendship, FriendshipId> {
    List<Friendship> findAllByUserId(Long userId);
}
