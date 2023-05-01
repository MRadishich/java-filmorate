package ru.yandex.practicum.filmorate.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class UserServiceTest {
    private UserStorage userStorage;
    private FriendStorage friendStorage;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userStorage = Mockito.mock(UserStorage.class);
        friendStorage = Mockito.mock(FriendStorage.class);
        userService = new UserServiceImpl(userStorage, friendStorage);
    }

    @Test
    void test1_shouldSaveNewUserInStorage() {
        User user = new User().toBuilder()
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        userService.create(user);

        Mockito.verify(userStorage, Mockito.times(1)).create(user);
    }

    @Test
    void test2_shouldReturnAllUsersFromStorage() {
        userService.findAll();

        Mockito.verify(userStorage, Mockito.times(1)).findAll();
    }

    @Test
    void test3_shouldReturnUserById() {
        userService.findById(1L);

        Mockito.verify(userStorage, Mockito.times(1)).findById(1L);
    }

    @Test
    void test4_shouldUpdateFilm() {
        User user = new User().toBuilder()
                .email("UpdatedEmail@email.ru")
                .login("UpdatedPetro20")
                .name("Updated Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        userService.update(user);

        Mockito.verify(userStorage, Mockito.times(1)).update(user);
    }

    @Test
    void test5_shouldDeleteUserById() {
        userService.deleteById(1L);

        Mockito.verify(userStorage, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void test6_shouldAddFriend() throws NoSuchFieldException, IllegalAccessException {
        userStorage = new InMemoryUserStorage();
        userService = new UserServiceImpl(userStorage, friendStorage);

        User user1 = new User().toBuilder()
                .id(1L)
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        User user2 = new User().toBuilder()
                .id(2L)
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        Field field = userStorage.getClass().getDeclaredField("users");
        field.setAccessible(true);
        Map<Long, User> users = (HashMap<Long, User>) field.get(userStorage);

        users.put(user1.getId(), user1);
        users.put(user2.getId(), user2);

        userService.addFriend(1L, 2L);

        Mockito.verify(friendStorage, Mockito.times(1)).addFriend(user1, user2);
    }

    @Test
    void test7_shouldDeleteFriend() throws NoSuchFieldException, IllegalAccessException {
        userStorage = new InMemoryUserStorage();
        userService = new UserServiceImpl(userStorage, friendStorage);

        User user1 = new User().toBuilder()
                .id(1L)
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        User user2 = new User().toBuilder()
                .id(2L)
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        Field field = userStorage.getClass().getDeclaredField("users");
        field.setAccessible(true);
        Map<Long, User> users = (HashMap<Long, User>) field.get(userStorage);

        users.put(user1.getId(), user1);
        users.put(user2.getId(), user2);

        userService.deleteFriend(1L, 2L);

        Mockito.verify(friendStorage, Mockito.times(1)).deleteFriend(user1, user2);
    }

    @Test
    void test8_shouldFindFriendsByUserId() {
        userService.findFriends(1L);

        Mockito.verify(friendStorage, Mockito.times(1)).findFriends(1L);
    }

    @Test
    void test9_shouldReturnCommonFriends() {
        userService.findCommonFriends(1L, 2L);

        Mockito.verify(friendStorage, Mockito.times(1)).findFriends(1L);
        Mockito.verify(friendStorage, Mockito.times(1)).findFriends(2L);
    }
}