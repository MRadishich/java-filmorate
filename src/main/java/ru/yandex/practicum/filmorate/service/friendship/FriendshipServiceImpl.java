package ru.yandex.practicum.filmorate.service.friendship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.friendship.Friendship;
import ru.yandex.practicum.filmorate.model.friendship.FriendshipId;
import ru.yandex.practicum.filmorate.model.friendship.FriendshipStatus;
import ru.yandex.practicum.filmorate.storage.friend.FriendDbStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendDbStorage friendStorage;

    @Override
    public void addFriend(long userId, long friendId) {
        FriendshipStatus status;

        if (friendStorage.existsById(new FriendshipId(friendId, userId))) {
            status = FriendshipStatus.APPROVED;
            friendStorage.save(new Friendship(new FriendshipId(friendId, userId), status));
        } else {
            status = FriendshipStatus.NOTAPPROVED;
        }

        friendStorage.save(new Friendship(new FriendshipId(userId, friendId), status));
    }

    @Override
    public void deleteFriend(long userId, long friendId) {
        friendStorage.delete(new Friendship(new FriendshipId(userId, friendId)));
        friendStorage.save(new Friendship(new FriendshipId(friendId, userId), FriendshipStatus.NOTAPPROVED));
    }

    @Override
    public List<Long> findFriendsIds(long userId) {
        return friendStorage.findAllByUserId(userId)
                .stream()
                .map(Friendship::getFriendId)
                .collect(Collectors.toList());
    }
}
