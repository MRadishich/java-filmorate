package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.filmorate.model.like.Like;

import java.util.List;

public interface LikeDbStorage extends JpaRepository<Like, Long> {

    @Query(value = "SELECT f.id FROM Film f LEFT JOIN likes l ON f.id = l.filmId GROUP BY f.id ORDER BY COUNT(l.userId) DESC")
    List<Long> findTopFilmIdByCountLikes(Pageable pageable);
}
