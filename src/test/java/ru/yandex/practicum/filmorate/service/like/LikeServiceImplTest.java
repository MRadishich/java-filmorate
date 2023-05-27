package ru.yandex.practicum.filmorate.service.like;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.like.Like;
import ru.yandex.practicum.filmorate.model.like.LikeId;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    @Mock
    private LikeDbStorage likeStorage;
    @Mock
    private FilmDbStorage filmStorage;
    @Mock
    private UserDbStorage userStorage;
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

        LikeId likeId = new LikeId(filmId, userId);
        Like like = new Like(likeId);

        given(filmStorage.existsById(filmId)).willReturn(true);
        given(userStorage.existsById(userId)).willReturn(true);

        // When
        likeService.addLike(filmId, userId);

        // Then
        Mockito.verify(likeStorage, Mockito.times(1)).save(like);
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

        LikeId likeId = new LikeId(filmId, userId);
        Like like = new Like(likeId);

        given(filmStorage.existsById(filmId)).willReturn(true);
        given(userStorage.existsById(userId)).willReturn(true);

        // When
        likeService.deleteLike(filmId, userId);

        // Then
        Mockito.verify(likeStorage, Mockito.times(1)).delete(like);
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

    @Test
    void findTopFilmsByLikes() {
        // Given
        int limit = 3;
        // When
        likeService.findTopFilmsByLikes(limit);

        // Then
        Mockito.verify(likeStorage, Mockito.times(1)).findTopFilmIdByCountLikes(PageRequest.of(0, limit));
    }
}