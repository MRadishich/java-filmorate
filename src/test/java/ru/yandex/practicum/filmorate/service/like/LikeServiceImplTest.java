package ru.yandex.practicum.filmorate.service.like;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    @Mock
    private LikeStorage likeStorage;
    @Mock
    private FilmStorage filmStorage;
    @Mock
    private UserStorage userStorage;
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        likeService = new LikeServiceImpl(
                likeStorage,
                filmStorage,
                userStorage
        );
    }

    @Test
    void test1_shouldAddLike() {
        // Given
        long filmId = 1L;
        long userId = 2L;

        given(filmStorage.existsById(filmId)).willReturn(true);
        given(userStorage.existsById(userId)).willReturn(true);

        // When
        likeService.addLike(filmId, userId);

        // Then
        Mockito.verify(likeStorage, Mockito.times(1)).saveLikeFilm(filmId, userId);
    }

    @Test
    void test2_shouldThrowNotFoundExceptionWhenTryAddLikeUnknownFilm() {
        // Given
        long filmId = 1L;
        long userId = 2L;

        given(filmStorage.existsById(filmId)).willReturn(false);
        String expectedMessage = "Фильм с id = " + filmId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> likeService.addLike(filmId, userId));


        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test3_shouldThrowNotFoundExceptionWhenTryAddLikeUnknownUser() {
        // Given
        long filmId = 1L;
        long userId = 2L;

        given(filmStorage.existsById(filmId)).willReturn(true);
        given(userStorage.existsById(userId)).willReturn(false);
        String expectedMessage = "Пользователь с id = " + userId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> likeService.addLike(filmId, userId));


        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test4_shouldDeleteLike() {
        // Given
        long filmId = 1L;
        long userId = 2L;

        given(filmStorage.existsById(filmId)).willReturn(true);
        given(userStorage.existsById(userId)).willReturn(true);

        // When
        likeService.deleteLike(filmId, userId);

        // Then
        Mockito.verify(likeStorage, Mockito.times(1)).deleteLikeFilm(filmId, userId);
    }

    @Test
    void test5_shouldThrowNotFoundExceptionWhenTryDeleteLikeUnknownFilm() {
        // Given
        long filmId = 1L;
        long userId = 2L;

        given(filmStorage.existsById(filmId)).willReturn(false);
        String expectedMessage = "Фильм с id = " + filmId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> likeService.deleteLike(filmId, userId));


        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test6_shouldThrowNotFoundExceptionWhenTryDeleteLikeUnknownUser() {
        // Given
        long filmId = 1L;
        long userId = 2L;

        given(filmStorage.existsById(filmId)).willReturn(true);
        given(userStorage.existsById(userId)).willReturn(false);
        String expectedMessage = "Пользователь с id = " + userId + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> likeService.deleteLike(filmId, userId));


        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}