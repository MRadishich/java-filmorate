package ru.yandex.practicum.filmorate.storage.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserStorageTest {

    UserStorage userStorage;

    @BeforeEach
    void setUp() {
        userStorage = new InMemoryUserStorage();
    }

    @Test
    void test1_shouldSaveNewUserInStorage() throws NoSuchFieldException, IllegalAccessException {
        User user = new User().toBuilder()
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        userStorage.create(user);

        Field field = userStorage.getClass().getDeclaredField("users");

        field.setAccessible(true);

        Map<Long, User> map = (HashMap<Long, User>) field.get(userStorage);

        assertEquals(user, map.get(1L));
    }

    @Test
    void test2_shouldReturnAllUsers() {
        User user1 = new User().toBuilder()
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        User user2 = new User().toBuilder()
                .email("email@ya.ru")
                .login("petro22")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        userStorage.create(user1);
        userStorage.create(user2);

        Collection<User> users = userStorage.findAll();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    void test3_shouldReturnUserById() {
        User user = new User().toBuilder()
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        userStorage.create(user);

        assertEquals(user, userStorage.findById(1L));
    }

    @Test
    void test4_shouldThrowExceptionIfTryToFindUnknownUser() {
        Exception exception = assertThrows(NotFoundException.class, () -> userStorage.findById(1L));

        String expectedMessage = "Пользователь с id = 1 не найден.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test5_shouldUpdateUserAndReturnUpdateUser() throws NoSuchFieldException, IllegalAccessException {
        User user = new User().toBuilder()
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        userStorage.create(user);

        User updatedUser = new User().toBuilder()
                .id(1L)
                .email("updatedEmail@email.ru")
                .login("updatedPetro20")
                .name("Updated Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        userStorage.update(updatedUser);

        Field field = userStorage.getClass().getDeclaredField("users");

        field.setAccessible(true);

        Map<Long, User> map = (HashMap<Long, User>) field.get(userStorage);

        assertEquals("updatedEmail@email.ru", map.get(1L).getEmail());
        assertEquals("updatedPetro20", map.get(1L).getLogin());
        assertEquals("Updated Petrov Petr Petrovich", map.get(1L).getName());
        assertEquals(LocalDate.now(), map.get(1L).getBirthday());
    }

    @Test
    void test6_shouldThrowExceptionIfUpdateUnknownUser() {
        User user = new User().toBuilder()
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        userStorage.create(user);

        User updatedUser = new User().toBuilder()
                .id(2L)
                .email("updatedEmail@email.ru")
                .login("updatedPetro20")
                .name("Updated Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        Exception exception = assertThrows(NotFoundException.class, () -> userStorage.update(updatedUser));
        String expectedMessage = "Пользователь с id = 2 не найден.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test7_shouldDeleteUserById() throws NoSuchFieldException, IllegalAccessException {
        User user = new User().toBuilder()
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        userStorage.create(user);

        Field field = userStorage.getClass().getDeclaredField("users");
        field.setAccessible(true);
        Map<Long, User> map = (HashMap<Long, User>) field.get(userStorage);

        assertEquals(1, map.size());

        userStorage.deleteById(1L);

        field = userStorage.getClass().getDeclaredField("users");
        field.setAccessible(true);
        map = (HashMap<Long, User>) field.get(userStorage);

        assertEquals(0, map.size());
    }

    @Test
    void test8_shouldTrowExceptionIfTryToDeleteUnknownUser() {
        Exception exception = assertThrows(NotFoundException.class, () -> userStorage.deleteById(1L));

        String expectedMessage = "Пользователь с id = 1 не найден.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test9_shouldSetNameOnLoginIfNameIsNull() throws NoSuchFieldException, IllegalAccessException {
        User user = new User().toBuilder()
                .email("email@email.ru")
                .login("petro20")
                .birthday(LocalDate.now())
                .build();

        userStorage.create(user);

        Field field = userStorage.getClass().getDeclaredField("users");

        field.setAccessible(true);

        Map<Long, User> map = (HashMap<Long, User>) field.get(userStorage);

        assertEquals(user.getLogin(), map.get(1L).getName());
    }
}