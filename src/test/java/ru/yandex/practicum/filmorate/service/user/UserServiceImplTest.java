package ru.yandex.practicum.filmorate.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.friendship.FriendshipService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserService userService;
    @Mock
    private UserStorage userStorage;
    @Mock
    private FriendshipService friendshipService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(
                userStorage,
                friendshipService
        );
    }


    @Test
    void test1_shouldSaveAndReturnNewUser() {
        // Given
        UserDTO userDTO = new UserDTO(
                1L,
                "email@email.ru",
                "petro20",
                "Petrov Petr Petrovich",
                LocalDate.now()
        );

        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        given(userStorage.save(user)).willReturn(user);

        // When
        UserDTO savedUser = userService.createUser(userDTO);

        // Then
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(userDTO.getEmail(), savedUser.getEmail());
        assertEquals(userDTO.getLogin(), savedUser.getLogin());
        assertEquals(userDTO.getName(), savedUser.getName());
        assertEquals(userDTO.getBirthday(), savedUser.getBirthday());
    }

    @Test
    void test2_shouldReturnAllUsersWrappedInDTO() {
        // Given
        List<User> users = List.of(
                new User().toBuilder()
                        .id(1L)
                        .email("email1@email.ru")
                        .login("petro1")
                        .name("Petrov1 Petr1 Petrovich1")
                        .birthday(LocalDate.now())
                        .build(),
                new User().toBuilder()
                        .id(2L)
                        .email("email2@email.ru")
                        .login("petro2")
                        .name("Petrov2 Petr2 Petrovich2")
                        .birthday(LocalDate.now())
                        .build(),
                new User().toBuilder()
                        .id(3L)
                        .email("email3@email.ru")
                        .login("petro3")
                        .name("Petrov3 Petr3 Petrovich3")
                        .birthday(LocalDate.now())
                        .build()
        );

        List<UserDTO> userDTOs = List.of(
                new UserDTO(
                        1L,
                        "email1@email.ru",
                        "petro1",
                        "Petrov1 Petr1 Petrovich1",
                        LocalDate.now()
                ),
                new UserDTO(
                        2L,
                        "email2@email.ru",
                        "petro2",
                        "Petrov2 Petr2 Petrovich2",
                        LocalDate.now()
                ),
                new UserDTO(
                        3L,
                        "email3@email.ru",
                        "petro3",
                        "Petrov3 Petr3 Petrovich3",
                        LocalDate.now()
                )
        );

        given(userStorage.findAll()).willReturn(users);

        // When
        Collection<UserDTO> foundUserDTOs = userService.findAllUsers();

        // Then
        assertEquals(userDTOs, foundUserDTOs);
    }


    @Test
    void test3_shouldReturnUserById() {
        // Given
        UserDTO userDTO = new UserDTO(
                1L,
                "email@email.ru",
                "petro20",
                "Petrov Petr Petrovich",
                LocalDate.now()
        );

        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        given(userStorage.findById(user.getId())).willReturn(Optional.of(user));

        // When
        UserDTO foundUserDTO = userService.findUserById(user.getId());

        // Then
        assertEquals(userDTO, foundUserDTO);
    }

    @Test
    public void test4_shouldThrowNotFoundExceptionIfUnknownUserId() {
        // Given
        long userId = 1L;

        given(userStorage.findById(userId)).willReturn(Optional.empty());
        String expectedMessage = "Пользователь с id = 1 не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> userService.findUserById(userId));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    public void test4_shouldUpdateUser() {
        // Given
        User user = new User().toBuilder()
                .id(1L)
                .email("updatedEmail@email.ru")
                .login("updatedLogin")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        UserDTO userDTO = new UserDTO(
                1L,
                "updatedEmail@email.ru",
                "updatedLogin",
                "Petrov Petr Petrovich",
                LocalDate.now()
        );

        given(userStorage.existsById(user.getId())).willReturn(true);
        given(userStorage.save(user)).willReturn(user);

        // When
        UserDTO updatedUserDTO = userService.updateUser(userDTO);

        // Then
        assertEquals(userDTO, updatedUserDTO);
    }


    @Test
    public void test5_shouldThrowNotFoundExceptionWhenUpdateUnknownUser() {
        // Given
        UserDTO userDTO = new UserDTO(
                1L,
                "updatedEmail@email.ru",
                "updatedLogin",
                "Petrov Petr Petrovich",
                LocalDate.now()
        );

        given(userStorage.existsById(userDTO.getId())).willReturn(false);
        String expectedMessage = "Пользователь с id = 1 не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> userService.updateUser(userDTO));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test6_shouldDeleteUserById() {
        // Given
        long userId = 1L;

        given(userStorage.existsById(userId)).willReturn(true);

        // When
        userService.deleteUserById(userId);

        // Then
        Mockito.verify(userStorage, Mockito.times(1)).deleteById(userId);
    }

    @Test
    public void test7_shouldThrowNotFoundExceptionWhenDeleteByUnknownUserId() {
        // Given
        long userId = 1L;

        given(userStorage.existsById(userId)).willReturn(false);
        String expectedMessage = "Пользователь с id = 1 не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> userService.deleteUserById(1L));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test8_shouldAddFriend() {
        // Given
        long userId = 1L;
        long friendId = 2L;

        given(userStorage.existsById(userId)).willReturn(true);
        given(userStorage.existsById(friendId)).willReturn(true);

        // When
        userService.addFriend(userId, friendId);

        // Then
        Mockito.verify(friendshipService, Mockito.times(1)).addFriend(userId, friendId);
    }

    @Test
    public void test9_shouldThrowExceptionWhenAddFriendIfUnknownUserId() {
        // Given
        long userId = 1L;
        long friendId = 2L;

        given(userStorage.existsById(userId)).willReturn(false);
        String expectedMessage = "Пользователь с id = " + userId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> userService.addFriend(userId, friendId));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test9_shouldThrowExceptionWhenAddFriendIfUnknownFriendId() {
        // Given
        long userId = 1L;
        long friendId = 2L;

        given(userStorage.existsById(userId)).willReturn(true);
        given(userStorage.existsById(friendId)).willReturn(false);
        String expectedMessage = "Пользователь с id = " + friendId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> userService.addFriend(userId, friendId));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test9_shouldDeleteFriend() {
        // Given
        long userId = 1L;
        long friendId = 2L;

        given(userStorage.existsById(userId)).willReturn(true);
        given(userStorage.existsById(friendId)).willReturn(true);

        // When
        userService.deleteFriend(userId, friendId);

        // Then
        Mockito.verify(friendshipService, Mockito.times(1)).deleteFriend(userId, friendId);
    }

    @Test
    public void test10_shouldThrowExceptionWhenDeleteFriendIfUnknownUserId() {
        // Given
        long userId = 1L;
        long friendId = 2L;

        given(userStorage.existsById(userId)).willReturn(false);
        String expectedMessage = "Пользователь с id = " + userId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> userService.deleteFriend(userId, friendId));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test11_shouldThrowExceptionWhenDeleteFriendIfUnknownFriendId() {
        // Given
        long userId = 1L;
        long friendId = 2L;

        given(userStorage.existsById(userId)).willReturn(true);
        given(userStorage.existsById(friendId)).willReturn(false);
        String expectedMessage = "Пользователь с id = " + friendId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> userService.deleteFriend(userId, friendId));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test12_shouldReturnFriendsByUserId() {
        // Given
        long userId = 1L;

        List<User> users = List.of(
                new User().toBuilder()
                        .id(1L)
                        .email("email1@email.ru")
                        .login("petro1")
                        .name("Petrov1 Petr1 Petrovich1")
                        .birthday(LocalDate.now())
                        .build(),
                new User().toBuilder()
                        .id(2L)
                        .email("email2@email.ru")
                        .login("petro2")
                        .name("Petrov2 Petr2 Petrovich2")
                        .birthday(LocalDate.now())
                        .build(),
                new User().toBuilder()
                        .id(3L)
                        .email("email3@email.ru")
                        .login("petro3")
                        .name("Petrov3 Petr3 Petrovich3")
                        .birthday(LocalDate.now())
                        .build()
        );

        List<UserDTO> userDTOs = List.of(
                new UserDTO(
                        1L,
                        "email1@email.ru",
                        "petro1",
                        "Petrov1 Petr1 Petrovich1",
                        LocalDate.now()
                ),
                new UserDTO(
                        2L,
                        "email2@email.ru",
                        "petro2",
                        "Petrov2 Petr2 Petrovich2",
                        LocalDate.now()
                ),
                new UserDTO(
                        3L,
                        "email3@email.ru",
                        "petro3",
                        "Petrov3 Petr3 Petrovich3",
                        LocalDate.now()
                )
        );

        given(userStorage.existsById(userId)).willReturn(true);
        given(friendshipService.findFriendsIds(userId)).willReturn(List.of());
        given(userStorage.findAllById(anyList())).willReturn(users);

        // Then
        Collection<UserDTO> foundFriendsDTO = userService.findFriends(userId);

        assertEquals(userDTOs, foundFriendsDTO);
    }

    @Test
    public void test13_shouldThrowNotFoundExceptionWhenTryFindFriendsByUnknownUserId() {
        // Given
        long userId = 1L;

        given(userStorage.existsById(userId)).willReturn(false);
        String expectedMessage = "Пользователь с id = " + userId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> userService.findFriends(userId));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test14_shouldFindCommonFriendsByIds() {
        long userId = 1L;
        long otherId = 2L;

        // Given
        List<User> users = List.of(
                new User().toBuilder()
                        .id(1L)
                        .email("email1@email.ru")
                        .login("petro1")
                        .name("Petrov1 Petr1 Petrovich1")
                        .birthday(LocalDate.now())
                        .build(),
                new User().toBuilder()
                        .id(2L)
                        .email("email2@email.ru")
                        .login("petro2")
                        .name("Petrov2 Petr2 Petrovich2")
                        .birthday(LocalDate.now())
                        .build(),
                new User().toBuilder()
                        .id(3L)
                        .email("email3@email.ru")
                        .login("petro3")
                        .name("Petrov3 Petr3 Petrovich3")
                        .birthday(LocalDate.now())
                        .build()
        );

        List<User> otherUsers = List.of(
                new User().toBuilder()
                        .id(1L)
                        .email("email1@email.ru")
                        .login("petro1")
                        .name("Petrov1 Petr1 Petrovich1")
                        .birthday(LocalDate.now())
                        .build(),
                new User().toBuilder()
                        .id(2L)
                        .email("email2@email.ru")
                        .login("petro2")
                        .name("Petrov2 Petr2 Petrovich2")
                        .birthday(LocalDate.now())
                        .build(),
                new User().toBuilder()
                        .id(4L)
                        .email("email4@email.ru")
                        .login("petro4")
                        .name("Petrov4 Petr4 Petrovich4")
                        .birthday(LocalDate.now())
                        .build()
        );

        List<UserDTO> expectedUserDTOs = List.of(
                new UserDTO(
                        1L,
                        "email1@email.ru",
                        "petro1",
                        "Petrov1 Petr1 Petrovich1",
                        LocalDate.now()
                ),
                new UserDTO(
                        2L,
                        "email2@email.ru",
                        "petro2",
                        "Petrov2 Petr2 Petrovich2",
                        LocalDate.now()
                )
        );

        given(userStorage.existsById(userId)).willReturn(true);
        given(userStorage.existsById(otherId)).willReturn(true);
        given(friendshipService.findFriendsIds(userId)).willReturn(List.of(1L, 2L, 3L));
        given(userStorage.findAllById(List.of(1L, 2L, 3L))).willReturn(users);
        given(friendshipService.findFriendsIds(otherId)).willReturn(List.of(1L, 2L, 4L));
        given(userStorage.findAllById(List.of(1L, 2L, 4L))).willReturn(otherUsers);

        // When
        Collection<UserDTO> commonFriends = userService.findCommonFriends(userId, otherId);

        // Then
        assertEquals(expectedUserDTOs, commonFriends);
    }

    @Test
    public void test15_shouldThrowNotFoundExceptionWhenTryFindCommonFriendsByUnknownUserId() {
        // Given
        long userId = 1L;
        long otherId = 2L;

        given(userStorage.existsById(userId)).willReturn(false);
        String expectedMessage = "Пользователь с id = " + userId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> userService.findCommonFriends(userId, otherId));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test16_shouldThrowNotFoundExceptionWhenTryFindCommonFriendsByUnknownOtherId() {
        // Given
        long userId = 1L;
        long otherId = 2L;

        given(userStorage.existsById(userId)).willReturn(true);
        given(userStorage.existsById(otherId)).willReturn(false);
        String expectedMessage = "Пользователь с id = " + otherId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> userService.findCommonFriends(userId, otherId));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}