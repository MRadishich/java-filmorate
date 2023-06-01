package ru.yandex.practicum.filmorate.service.friendship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.friendship.Friendship;
import ru.yandex.practicum.filmorate.model.friendship.FriendshipStatus;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendStorage friendStorage;

    @Override
    @Transactional
    public void addFriend(long userId, long friendId) {
        FriendshipStatus status;

        if (friendStorage.existsById(new Friendship(friendId, userId))) {
            status = FriendshipStatus.APPROVED;
            friendStorage.save(new Friendship(friendId, userId, status));
        } else {
            status = FriendshipStatus.NOTAPPROVED;
        }

        friendStorage.save(new Friendship(userId, friendId, status));
    }

    @Override
    @Transactional
    public void deleteFriend(long userId, long friendId) {
        friendStorage.delete(new Friendship(userId, friendId));
        friendStorage.save(new Friendship(friendId, userId, FriendshipStatus.NOTAPPROVED));
    }

    @Override
    @Transactional
    public List<Long> findFriendsIds(long userId) {
        return friendStorage.findAllByUserId(userId)
                .stream()
                .map(Friendship::getFriendId)
                .collect(Collectors.toList());
    }
}
