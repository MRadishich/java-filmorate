package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
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
    public ResponseEntity<FilmDTO> createFilm(@Valid @RequestBody FilmDTO film) {
        return new ResponseEntity<>(filmService.createFilm(film), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FilmDTO>> findAllFilms() {
        List<FilmDTO> films = filmService.findAllFilms();

        return films != null ?
                new ResponseEntity<>(films, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> findFilmById(@PathVariable("id") long id) {
        return new ResponseEntity<>(filmService.findFilmById(id), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<FilmDTO>> findPopularFilms(@RequestParam(value = "count", defaultValue = "10") int count) {
        return new ResponseEntity<>(filmService.findPopularFilms(count), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FilmDTO> updateFilm(@Valid @RequestBody FilmDTO film) {
        return new ResponseEntity<>(filmService.updateFilm(film), HttpStatus.OK);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<?> addLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        likeService.addLike(filmId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllFilms() {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilmById(@PathVariable("id") long id) {
        filmService.deleteFilmById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        likeService.deleteLike(filmId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
