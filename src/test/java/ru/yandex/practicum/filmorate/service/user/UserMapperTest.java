package ru.yandex.practicum.filmorate.service.user;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    @Test
    void test1_shouldConversionUserToUserDTO() {
        // Given
        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.ru")
                .login("petro20")
                .name("Petrov Petr Petrovich")
                .birthday(LocalDate.now())
                .build();

        UserDto userDTO = new UserDto(
                1L,
                "email@email.ru",
                "petro20",
                "Petrov Petr Petrovich",
                LocalDate.now()
        );

        // When
        UserDto convertedUserDTO = UserMapper.toDto(user);

        // Then
        assertEquals(userDTO, convertedUserDTO);
    }

    @Test
    void test2_shouldConversionUserDTOToUser() {
        // Given
        UserDto userDTO = new UserDto(
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

        // When
        User convertedUser = UserMapper.toUser(userDTO);

        // Then
        assertEquals(user, convertedUser);
    }

    @Test
    void test3_shouldConversionUserDTOToUserAndSetNameIfNull() {
        // Given
        UserDto userDTO = new UserDto(
                1L,
                "email@email.ru",
                "petro20",
                null,
                LocalDate.now()
        );

        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.ru")
                .login("petro20")
                .name("petro20")
                .birthday(LocalDate.now())
                .build();

        // When
        User convertedUser = UserMapper.toUser(userDTO);

        // Then
        assertEquals(user, convertedUser);
    }

    @Test
    void test4_shouldConversionUserDTOToUserAndSetNameIfEmpty() {
        // Given
        UserDto userDTO = new UserDto(
                1L,
                "email@email.ru",
                "petro20",
                "",
                LocalDate.now()
        );

        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.ru")
                .login("petro20")
                .name("petro20")
                .birthday(LocalDate.now())
                .build();

        // When
        User convertedUser = UserMapper.toUser(userDTO);

        // Then
        assertEquals(user, convertedUser);
    }

    @Test
    void test5_shouldConversionUserDTOToUserAndSetNameIfBlank() {
        // Given
        UserDto userDTO = new UserDto(
                1L,
                "email@email.ru",
                "petro20",
                "  ",
                LocalDate.now()
        );

        User user = new User().toBuilder()
                .id(1L)
                .email("email@email.ru")
                .login("petro20")
                .name("petro20")
                .birthday(LocalDate.now())
                .build();

        // When
        User convertedUser = UserMapper.toUser(userDTO);

        // Then
        assertEquals(user, convertedUser);
    }

}