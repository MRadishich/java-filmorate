package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserStorageTest {
    private final UserDbStorage userStorage;
    private User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User().toBuilder()
                .name("User")
                .login("SuperUser")
                .email("email@email.ru")
                .birthday(LocalDate.now())
                .build();
    }

    @Test
    public void test1_shouldSaveUser() {
        User savedUser = userStorage.save(newUser);

        assertThat(savedUser).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(savedUser).hasFieldOrPropertyWithValue("name", "User");
        assertThat(savedUser).hasFieldOrPropertyWithValue("login", "SuperUser");
        assertThat(savedUser).hasFieldOrPropertyWithValue("email", "email@email.ru");
        assertThat(savedUser).hasFieldOrPropertyWithValue("birthday", LocalDate.now());
    }

    @Test
    public void test2_shouldUpdateUser() {
        userStorage.save(newUser);

        newUser.setName("Updated User");
        newUser.setLogin("UpdateSuperUser");

        User updatedUser = userStorage.save(newUser);

        assertThat(updatedUser).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(updatedUser).hasFieldOrPropertyWithValue("name", "Updated User");
        assertThat(updatedUser).hasFieldOrPropertyWithValue("login", "UpdateSuperUser");
        assertThat(updatedUser).hasFieldOrPropertyWithValue("email", "email@email.ru");
        assertThat(updatedUser).hasFieldOrPropertyWithValue("birthday", LocalDate.now());
    }

    @Test
    public void test3_shouldReturnUserById() {
        userStorage.save(newUser);

        Optional<User> foundUser = userStorage.findById(1L);

        assertThat(foundUser)
                .isPresent()
                .hasValueSatisfying(user -> {
                    assertThat(user).hasFieldOrPropertyWithValue("id", 1L);
                    assertThat(user).hasFieldOrPropertyWithValue("name", "User");
                    assertThat(user).hasFieldOrPropertyWithValue("login", "SuperUser");
                    assertThat(user).hasFieldOrPropertyWithValue("email", "email@email.ru");
                    assertThat(user).hasFieldOrPropertyWithValue("birthday", LocalDate.now());
                });
    }

    @Test
    public void test4_shouldReturnAllUsers() {
        userStorage.save(newUser);

        List<User> users = userStorage.findAll();

        assertEquals(1, users.size());
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("name", "User");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("login", "SuperUser");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("email", "email@email.ru");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("birthday", LocalDate.now());

        User otherUser = new User().toBuilder()
                .name("Other User")
                .login("OtherSuperUser")
                .email("OtherEmail@email.ru")
                .birthday(LocalDate.now())
                .build();

        userStorage.save(otherUser);

        users = userStorage.findAll();

        assertEquals(2, users.size());
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("name", "User");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("login", "SuperUser");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("email", "email@email.ru");
        assertThat(users.get(0)).hasFieldOrPropertyWithValue("birthday", LocalDate.now());
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("id", 2L);
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("name", "Other User");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("login", "OtherSuperUser");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("email", "OtherEmail@email.ru");
        assertThat(users.get(1)).hasFieldOrPropertyWithValue("birthday", LocalDate.now());
    }

    @Test
    public void test5_shouldReturnUsersByListIds() {
        userStorage.save(newUser);

        List<User> users = userStorage.findAll();

        User otherUser = new User().toBuilder()
                .name("Other User #1")
                .login("OtherSuperUserOne")
                .email("OtherEmailOne@email.ru")
                .birthday(LocalDate.now())
                .build();

        userStorage.save(otherUser);

        otherUser = new User().toBuilder()
                .name("Other User #2")
                .login("OtherSuperUserTwo")
                .email("OtherEmailTwo@email.ru")
                .birthday(LocalDate.now())
                .build();

        userStorage.save(otherUser);

        List<Long> userIds = List.of(1L, 2L);
        List<User> foundUsers = userStorage.findAllById(userIds);

        assertEquals(2, foundUsers.size());
        assertThat(foundUsers.get(0)).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(foundUsers.get(0)).hasFieldOrPropertyWithValue("name", "User");
        assertThat(foundUsers.get(0)).hasFieldOrPropertyWithValue("login", "SuperUser");
        assertThat(foundUsers.get(0)).hasFieldOrPropertyWithValue("email", "email@email.ru");
        assertThat(foundUsers.get(0)).hasFieldOrPropertyWithValue("birthday", LocalDate.now());
        assertThat(foundUsers.get(1)).hasFieldOrPropertyWithValue("id", 2L);
        assertThat(foundUsers.get(1)).hasFieldOrPropertyWithValue("name", "Other User #1");
        assertThat(foundUsers.get(1)).hasFieldOrPropertyWithValue("login", "OtherSuperUserOne");
        assertThat(foundUsers.get(1)).hasFieldOrPropertyWithValue("email", "OtherEmailOne@email.ru");
        assertThat(foundUsers.get(1)).hasFieldOrPropertyWithValue("birthday", LocalDate.now());
    }

    @Test
    public void test6_shouldReturnTrueIfUserExists() {
        userStorage.save(newUser);

        assertTrue(userStorage.existsById(1L));
    }

    @Test
    public void test7_shouldReturnFalseIfUserExists() {

        assertFalse(userStorage.existsById(1L));
    }
}
