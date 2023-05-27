package ru.yandex.practicum.filmorate.storage.friend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.yandex.practicum.filmorate.model.friendship.Friendship;
import ru.yandex.practicum.filmorate.model.friendship.FriendshipId;
import ru.yandex.practicum.filmorate.model.friendship.FriendshipStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FriendDbStorageTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private FriendDbStorage friendDbStorage;

    @Test
    public void test1_shouldReturnAllFriendshipWhenFindByUserId() {
        // Given
        List<Friendship> friendships = List.of(
                new Friendship(
                        new FriendshipId(1L, 2L),
                        FriendshipStatus.NOTAPPROVED),
                new Friendship(
                        new FriendshipId(1L, 3L),
                        FriendshipStatus.NOTAPPROVED),
                new Friendship(
                        new FriendshipId(1L, 4L),
                        FriendshipStatus.NOTAPPROVED)
        );

        friendships.forEach(f -> entityManager.persist(f));

        // When
        List<Friendship> found = friendDbStorage.findAllByUserId(1L);

        assertEquals(friendships, found);
    }
}