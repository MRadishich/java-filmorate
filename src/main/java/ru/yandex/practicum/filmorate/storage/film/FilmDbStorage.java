package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Film;

@Repository
public interface FilmDbStorage extends JpaRepository<Film, Long> {
}
