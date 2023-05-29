package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.service.like.LikeService;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final GenreDbStorage genreStorage;
    private final FilmDbStorage filmStorage;
    private final LikeService likeService;

    public FilmDTO createFilm(FilmDTO filmDTO) {
        log.info("Получен запрос на создание нового фильма: {}", filmDTO);

        Film film = FilmMapper.toFilm(filmDTO);
        film.setGenres(getGenresByIds(filmDTO));
        film = filmStorage.save(film);

        return FilmMapper.toDto(film);
    }

    private List<Genre> getGenresByIds(FilmDTO filmDTO) {
        log.info("Получаем список жанров по их id");

        if (filmDTO.getGenres() == null) {
            return List.of();
        }

        return genreStorage.findAllById(
                filmDTO.getGenres()
                        .stream()
                        .map(Genre::getId)
                        .collect(Collectors.toList()));
    }

    public Collection<FilmDTO> findAllFilms() {
        log.info("Получен запрос на поиск всех фильмов.");

        return filmStorage.findAll()
                .stream()
                .map(FilmMapper::toDto)
                .collect(Collectors.toList());
    }

    public FilmDTO findFilmById(Long filmId) {
        log.info("Получен запрос на поиск фильма по filmId = {}", filmId);

        return filmStorage.findById(filmId)
                .map(FilmMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Фильм с id = " + filmId + " не найден."));
    }

    public FilmDTO updateFilm(FilmDTO filmDTO) {
        final Long filmId = filmDTO.getId();

        log.info("Получен запрос на обновление фильма с id = {}. Новое значение: {}", filmId, filmDTO);

        if (!filmStorage.existsById(filmId)) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден.");
        }

        Film film = FilmMapper.toFilm((filmDTO));
        film.setGenres(getGenresByIds(filmDTO));
        film = filmStorage.save(film);

        return FilmMapper.toDto(filmStorage.save(film));
    }

    public void deleteFilmById(Long filmId) {
        log.info("Получен запрос на удаление фильма с filmId = {}.", filmId);

        if (!filmStorage.existsById(filmId)) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден.");
        }

        filmStorage.deleteById(filmId);
    }

    public Collection<FilmDTO> findPopularFilms(int limit) {
        return filmStorage.findAllById(
                        likeService.findTopFilmsByLikes(limit))
                .stream()
                .map(FilmMapper::toDto)
                .collect(Collectors.toList());
    }
}
