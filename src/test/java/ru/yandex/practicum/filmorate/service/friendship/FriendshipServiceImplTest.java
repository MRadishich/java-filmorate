package ru.yandex.practicum.filmorate.service.friendship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.enums.FriendshipStatus;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceImplTest {
    @Mock
    private FriendStorage friendStorage;
    private FriendshipService friendshipService;

    @BeforeEach
    void setUp() {
        friendshipService = new FriendshipServiceImpl(friendStorage);
    }

    @Test
    public void test1_shouldAddFriendWithNotApprovedStatus() {
        // Given
        long userId = 1L;
        long friendId = 2L;

        given(friendStorage.existsById(friendId, userId)).willReturn(false);

        // When
        friendshipService.addFriend(userId, friendId);

        // Then
        Mockito.verify(friendStorage).save(userId, friendId, FriendshipStatus.NOTAPPROVED);
    }

    @Test
    public void test2_shouldAddFriendWithApprovedStatus() {
        // Given
        long userId = 1L;
        long friendId = 2L;

        given(friendStorage.existsById(friendId, userId)).willReturn(true);

        // When
        friendshipService.addFriend(userId, friendId);

        // Then
        Mockito.verify(friendStorage).save(userId, friendId, FriendshipStatus.APPROVED);
    }

    @Test
    public void test3_shouldSetFriendshipStatusWhenDeleteFriend() {
        // Given
        long userId = 1L;
        long friendId = 2L;

        // When
        friendshipService.deleteFriend(userId, friendId);

        // Then
        Mockito.verify(friendStorage).delete(userId, friendId);
        Mockito.verify(friendStorage).save(friendId, userId, FriendshipStatus.NOTAPPROVED);
    }

    @Test
    void test4_shouldReturnFriendsIds() {
        // Given
        long userId = 1L;

        given(friendStorage.findAllFriendIdsByUserId(userId))
                .willReturn(List.of(2L, 3L, 4L));

        // When
        List<Long> friendsIds = friendshipService.getFriendsIds(userId);

        // Then
        assertEquals(List.of(2L, 3L, 4L), friendsIds);
    }
}