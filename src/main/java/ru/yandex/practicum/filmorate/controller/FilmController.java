package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService service;

    @PostMapping
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        return new ResponseEntity<>(service.create(film), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Film>> findAll() {
        Collection<Film> films = service.findAll();

        return films != null ?
                new ResponseEntity<>(films, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> findById(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<Collection<Film>> findPopular(@RequestParam(value = "count", defaultValue = "10") long count) {
        return new ResponseEntity<>(service.findPopular(count), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {
        return new ResponseEntity<>(service.update(film), HttpStatus.OK);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<?> like(@PathVariable("id") long filmId, @PathVariable long userId) {
        service.addLike(filmId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<?> deleteLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        service.deleteLike(filmId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
