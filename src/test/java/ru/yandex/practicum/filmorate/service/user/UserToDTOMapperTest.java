package ru.yandex.practicum.filmorate.service.user;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.model.user.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserToDTOMapperTest {
    private final UserToDTOMapper userToDTOMapper = new UserToDTOMapper();

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

        UserDTO userDTO = new UserDTO(
                1L,
                "email@email.ru",
                "petro20",
                "Petrov Petr Petrovich",
                LocalDate.now()
        );

        // When
        UserDTO convertedUserDTO = userToDTOMapper.apply(user);

        // Then
        assertEquals(userDTO, convertedUserDTO);
    }

}