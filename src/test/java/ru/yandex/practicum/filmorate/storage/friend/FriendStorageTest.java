package ru.yandex.practicum.filmorate.storage.friend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FriendStorageTest {
    private FriendStorage friendStorage;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        friendStorage = new InMemoryFriendStorage();

        user1 = new User().toBuilder()
                .id(1L)
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        user2 = new User().toBuilder()
                .id(2L)
                .email("email@mail.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        user3 = new User().toBuilder()
                .id(3L)
                .email("email@ya.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        friendStorage.addFriend(user1, user2);
        friendStorage.addFriend(user1, user3);
    }

    @Test
    void test1_shouldReturnAllFriendsUserWithId1() {
        Collection<User> expectedFriends = Set.of(user2, user3);

        assertEquals(expectedFriends, friendStorage.findFriends(1L));
    }

    @Test
    void test2_shouldReturnEmptyLisFriendsUserWithId4() {
        assertTrue(friendStorage.findFriends(4L).isEmpty());
    }

    @Test
    void test3_shouldAddFriend() {
        User user4 = new User().toBuilder()
                .id(4L)
                .email("email@gmail.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        friendStorage.addFriend(user2, user4);

        assertEquals(1, friendStorage.findFriends(4L).size());
        assertTrue(friendStorage.findFriends(4L).contains(user2));
        assertTrue(friendStorage.findFriends(2L).contains(user4));
    }

    @Test
    void test4_shouldDeleteFriend() {
        friendStorage.deleteFriend(user1, user2);

        assertEquals(1, friendStorage.findFriends(1L).size());
        assertEquals(0, friendStorage.findFriends(2L).size());
        assertFalse(friendStorage.findFriends(1L).contains(user2));
        assertFalse(friendStorage.findFriends(2L).contains(user1));
    }
}