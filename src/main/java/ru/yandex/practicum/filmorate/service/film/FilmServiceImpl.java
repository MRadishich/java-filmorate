package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.service.like.LikeService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final GenreStorage genreStorage;
    private final FilmStorage filmStorage;
    private final LikeService likeService;
    private final MpaStorage mpaStorage;

    @Override
    @Transactional
    public FilmDTO createFilm(FilmDTO filmDTO) {
        log.info("Получен запрос на создание нового фильма: {}", filmDTO);

        Film film = FilmMapper.toFilm(filmDTO);

        film = filmStorage.save(film);

        film.setGenres(getGenresByIds(filmDTO));

        film.setMpa(mpaStorage.findById(film.getMpa().getId()).orElse(null));


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

    @Override
    @Transactional
    public Collection<FilmDTO> findAllFilms() {
        log.info("Получен запрос на поиск всех фильмов.");

        return filmStorage.findAll()
                .stream()
                .map(FilmMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FilmDTO findFilmById(Long filmId) {
        log.info("Получен запрос на поиск фильма по filmId = {}", filmId);

        return filmStorage.findById(filmId)
                .map(FilmMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Фильм с id = " + filmId + " не найден."));
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void deleteFilmById(Long filmId) {
        log.info("Получен запрос на удаление фильма с filmId = {}.", filmId);

        if (!filmStorage.existsById(filmId)) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден.");
        }

        filmStorage.deleteById(filmId);
    }

    @Override
    @Transactional
    public Collection<FilmDTO> findPopularFilms(int limit) {
        return filmStorage.findAllById(
                        likeService.findTopFilmsByLikes(limit))
                .stream()
                .map(FilmMapper::toDto)
                .collect(Collectors.toList());
    }
}
