package ru.yandex.practicum.filmorate.storage.filmGenre;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FilmGenreDbStorage {
    Map<Long, List<Genre>> getAllFilmGenres(Collection<Film> films);
}
