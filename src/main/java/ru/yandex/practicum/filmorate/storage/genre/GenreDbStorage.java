package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Genre;

@Repository
public interface GenreDbStorage extends JpaRepository<Genre, Integer> {

}
