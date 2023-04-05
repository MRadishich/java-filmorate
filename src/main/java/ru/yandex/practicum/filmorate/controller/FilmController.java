package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmRepository repository;

    @Autowired
    public FilmController(FilmRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Film film) {
        repository.create(film);

        log.info("Создан новый фильм: {}", film);

        return new ResponseEntity<>(film, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Film>> getAll() {
        Collection<Film> films = repository.getAll();

        return films != null ?
                new ResponseEntity<>(films, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(repository.get(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Film film) {
        try {
            Film newFilm = repository.update(film);
            log.info("Обновлен фильм с id = {}. Новое значение: {}", film.getId(), newFilm);
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (NotFoundException e) {
            log.info("{} Обновление не возможно.", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        try {
            repository.delete(id);
            log.info("Удален фильм с id = {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.info("{} Удаление не возможно.", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
