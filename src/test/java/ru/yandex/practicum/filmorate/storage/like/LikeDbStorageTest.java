package ru.yandex.practicum.filmorate.storage.like;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.like.Like;
import ru.yandex.practicum.filmorate.model.like.LikeId;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class LikeDbStorageTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LikeDbStorage likeStorage;

    @Test
    void test1_shouldReturnOneFilmIdByCountLikes() {
        // Given
        int numberOfFilms = 1;
        List<Film> films = List.of(
                new Film().toBuilder()
                        .name("Film 1")
                        .duration(100)
                        .build(),
                new Film()
                        .toBuilder()
                        .name("Film 1")
                        .duration(100)
                        .build(),
                new Film().toBuilder()
                        .name("Film 1")
                        .duration(100)
                        .build()
        );

        films.forEach(f -> entityManager.persist(f));

        List<Like> likes = List.of(
                new Like(new LikeId(1L, 1L)),
                new Like(new LikeId(1L, 2L)),
                new Like(new LikeId(1L, 3L)),
                new Like(new LikeId(2L, 1L)),
                new Like(new LikeId(2L, 2L)),
                new Like(new LikeId(3L, 1L)),
                new Like(new LikeId(3L, 2L)),
                new Like(new LikeId(3L, 3L)),
                new Like(new LikeId(3L, 4L))
        );

        likes.forEach(l -> entityManager.persist(l));

        // When
        List<Long> filmIds = likeStorage.findTopFilmIdByCountLikes(PageRequest.of(0, numberOfFilms));

        // Then
        assertEquals(1, filmIds.size());
        assertEquals(3, filmIds.get(0));
    }
}