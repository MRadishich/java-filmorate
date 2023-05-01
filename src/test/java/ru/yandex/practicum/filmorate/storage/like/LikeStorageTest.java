package ru.yandex.practicum.filmorate.storage.like;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LikeStorageTest {

    LikeStorage likeStorage;

    @BeforeEach
    void setUp() {
        likeStorage = new InMemoryLikeStorage();
        likeStorage.create(1L);
        likeStorage.create(2L);
        likeStorage.create(3L);
        likeStorage.create(4L);
        likeStorage.create(5L);
        likeStorage.create(6L);
        likeStorage.addLike(1L, 1L);
        likeStorage.addLike(2L, 2L);
        likeStorage.addLike(3L, 1L);
        likeStorage.addLike(4L, 5L);
        likeStorage.addLike(4L, 1L);
    }

    @Test
    void test1_shouldAddLikeFilm() throws NoSuchFieldException, IllegalAccessException {
        likeStorage.addLike(4L, 3L);

        Field field = likeStorage.getClass().getDeclaredField("likes");

        field.setAccessible(true);

        Map<Long, Set<Long>> map = (HashMap<Long, Set<Long>>) field.get(likeStorage);

        assertEquals(6, map.size());
        assertEquals(Set.of(5L, 1L, 3L), map.get(4L));
    }

    @Test
    void test2_shouldDeleteLike() throws NoSuchFieldException, IllegalAccessException {
        likeStorage.deleteLike(4L, 1L);

        Field field = likeStorage.getClass().getDeclaredField("likes");

        field.setAccessible(true);

        Map<Long, Set<Long>> map = (HashMap<Long, Set<Long>>) field.get(likeStorage);

        assertEquals(6, map.size());
        assertEquals(Set.of(5L), map.get(4L));
    }

    @Test
    void test3_shouldThrowExceptionIfTryDeleteLikeUnknownUser() {
        Exception exception = assertThrows(NotFoundException.class, () -> likeStorage.deleteLike(4L, 3L));

        String expectedMessage = "Лайк от пользователя с id = 3 не найден.";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void test4_shouldReturn3MostPopularFilm() {
        Collection<Long> films = likeStorage.findPopular(3);

        assertEquals(Set.of(4L, 1L, 2L), new HashSet<>(films));
    }

    @Test
    void test5_shouldReturnAllFilms() {
        likeStorage = new InMemoryLikeStorage();
        likeStorage.create(1L);
        likeStorage.create(2L);
        likeStorage.create(3L);
        likeStorage.create(4L);

        Collection<Long> films = likeStorage.findPopular(10);

        assertEquals(Set.of(1L, 2L, 3L, 4L), new HashSet<>(films));
    }
}