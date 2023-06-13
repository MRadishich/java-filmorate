package ru.yandex.practicum.filmorate.service.friendship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.enums.FriendshipStatus;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendStorage friendStorage;

    @Override
    @Transactional
    public void addFriend(long userId, long friendId) {
        FriendshipStatus status;

        if (friendStorage.existsById(friendId, userId)) {
            status = FriendshipStatus.APPROVED;
            friendStorage.save(friendId, userId, status);
        } else {
            status = FriendshipStatus.NOTAPPROVED;
        }

        friendStorage.save(userId, friendId, status);
    }

    @Override
    @Transactional
    public void deleteFriend(long userId, long friendId) {
        friendStorage.delete(userId, friendId);
        friendStorage.save(friendId, userId, FriendshipStatus.NOTAPPROVED);
    }

    @Override
    @Transactional
    public List<Long> getFriendsIds(long userId) {
        return friendStorage.findAllFriendIdsByUserId(userId);
    }
}
