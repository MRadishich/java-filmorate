package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenreStorage {
    Optional<Genre> findById(int id);

    List<Genre> findAll();

    List<Genre> findAllById(List<Integer> ids);

    List<Genre> findAllByFilmId(long filmId);

    Map<Long, List<Genre>> getGenresByFilms(Collection<Film> films);
}
