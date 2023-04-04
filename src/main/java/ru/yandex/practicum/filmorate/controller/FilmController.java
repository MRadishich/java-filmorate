package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody Film film) {
        repository.create(film);

        log.info("Создан новый фильм: {}", film);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Location", "/films/" + film.getId());

        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Collection<Film>> getAll() {
        Collection<Film> films = repository.getAll();

        return films != null ?
                new ResponseEntity<>(films, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> get(@PathVariable int id) {
        Film film = repository.get(id);

        return film != null ?
                new ResponseEntity<>(film, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@Valid @PathVariable(name = "id") int id, @RequestBody Film film) {
        boolean isUpdated = repository.update(film);
        if (isUpdated) {
            log.info("Обновлен фильм с id = {}. Новое значение: {}", id, film);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        log.info("Фильм с id = {} не найден. Обновление не возможно.", id);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        boolean isDeleted = repository.delete(id);

        if (isDeleted) {
            log.info("Удален фильм с id = {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        log.info("Фильм с id = {} не найден. Удаление не возможно.", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
