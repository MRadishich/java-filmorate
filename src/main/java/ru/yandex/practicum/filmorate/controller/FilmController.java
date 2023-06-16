package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.like.LikeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<FilmDto> createFilm(@Valid @RequestBody FilmDto film) {
        return new ResponseEntity<>(filmService.createFilm(film), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FilmDto>> findAllFilms() {
        List<FilmDto> films = filmService.getAllFilms();

        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> findFilmById(@PathVariable(value = "id") long filmId) {
        return new ResponseEntity<>(filmService.getFilmById(filmId), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<FilmDto>> findPopularFilms(@RequestParam(value = "count", defaultValue = "10") int count) {
        return new ResponseEntity<>(filmService.getPopularFilms(count), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FilmDto> updateFilm(@Valid @RequestBody FilmDto film) {
        return new ResponseEntity<>(filmService.updateFilm(film), HttpStatus.OK);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<?> addLike(@PathVariable(value = "id") long filmId, @PathVariable long userId) {
        likeService.addLike(filmId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllFilms() {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilmById(@PathVariable(value = "id") long id) {
        filmService.deleteFilmById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable(value = "id") long filmId, @PathVariable long userId) {
        likeService.deleteLike(filmId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
