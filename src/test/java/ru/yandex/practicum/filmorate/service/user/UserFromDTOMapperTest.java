package ru.yandex.practicum.filmorate.service.user;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.model.user.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserFromDTOMapperTest {

    private final UserFromDTOMapper userFromDTOMapper = new UserFromDTOMapper();

    @Test
    void test1_shouldConversionUserDTOToUser() {
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

        // When
        User convertedUser = userFromDTOMapper.apply(userDTO);

        // Then
        assertEquals(user, convertedUser);
    }

    @Test
    void test2_shouldConversionUserDTOToUserAndSetNameIfNull() {
        // Given
        UserDTO userDTO = new UserDTO(
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
        User convertedUser = userFromDTOMapper.apply(userDTO);

        // Then
        assertEquals(user, convertedUser);
    }

    @Test
    void test3_shouldConversionUserDTOToUserAndSetNameIfEmpty() {
        // Given
        UserDTO userDTO = new UserDTO(
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
        User convertedUser = userFromDTOMapper.apply(userDTO);

        // Then
        assertEquals(user, convertedUser);
    }

    @Test
    void test4_shouldConversionUserDTOToUserAndSetNameIfBlank() {
        // Given
        UserDTO userDTO = new UserDTO(
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
        User convertedUser = userFromDTOMapper.apply(userDTO);

        // Then
        assertEquals(user, convertedUser);
    }
}