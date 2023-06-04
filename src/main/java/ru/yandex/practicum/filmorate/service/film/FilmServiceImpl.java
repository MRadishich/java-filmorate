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
import ru.yandex.practicum.filmorate.storage.filmGenre.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final GenreStorage genreStorage;
    private final FilmStorage filmStorage;
    private final MpaStorage mpaStorage;
    private final FilmGenreStorage filmGenreStorage;
    private final LikeService likeService;

    @Override
    @Transactional
    public FilmDTO createFilm(FilmDTO filmDTO) {
        log.info("Получен запрос на создание нового фильма: {}", filmDTO);

        Film film = FilmMapper.toFilm(filmDTO);

        film = filmStorage.save(film);
        film.setGenres(getGenresByIds(film.getGenres()));
        film.setMpa(mpaStorage.findById(film.getMpa().getId()).orElse(null));

        return FilmMapper.toDto(film);
    }

    private List<Genre> getGenresByIds(List<Genre> genres) {
        log.info("Получаем список жанров по их id");

        if (genres == null) {
            return List.of();
        }

        return genreStorage.findAllById(genres
                .stream()
                .map(Genre::getId)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public List<FilmDTO> findAllFilms() {
        log.info("Получен запрос на поиск всех фильмов.");

        List<Film> films = filmStorage.findAll();

        setGenres(films);

        return films.stream()
                .map(FilmMapper::toDto)
                .collect(Collectors.toList());
    }

    private void setGenres(List<Film> films) {
        Map<Long, List<Genre>> allFilmGenres = filmGenreStorage.getAllFilmGenres(films);

        films.forEach(film -> {
            Long filmId = film.getId();
            film.setGenres(allFilmGenres.getOrDefault(filmId, new ArrayList<>()));
        });
    }

    @Override
    @Transactional
    public FilmDTO findFilmById(Long filmId) {
        log.info("Получен запрос на поиск фильма по filmId = {}", filmId);

        Film film = filmStorage.findById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с id = " + filmId + " не найден."));

        film.setGenres(genreStorage.findAllByFilmId(filmId));

        return FilmMapper.toDto(film);
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
        film = filmStorage.save(film);
        film.setGenres(getGenresByIds(film.getGenres()));

        return FilmMapper.toDto(film);
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
    public List<FilmDTO> findPopularFilms(int limit) {

        List<Film> films = filmStorage.findAllById(
                likeService.findTopFilmsByLikes(limit)
        );

        setGenres(films);

        return films.stream()
                .map(FilmMapper::toDto)
                .collect(Collectors.toList());
    }
}
