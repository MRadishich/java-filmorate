package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    public Film create(@Validated @RequestBody Film film) {
        repository.create(film);
        log.info("Создан новый фильм: {}", film);
        return film;
    }

    @GetMapping()
    public Collection<Film> readAll() {
        return repository.readAll();
    }

    @GetMapping("/{id}")
    public Film read(@PathVariable int id) {
        return repository.read(id);
    }

    @PutMapping("/{id}")
    public Film update(@Validated() @RequestBody Film film) {
        Film oldFilm = repository.update(film);
        log.info("Обновление фильма. Старое значение: {}, Новое значение: {}.", oldFilm, film);
        return film;
    }

    @DeleteMapping()
    public void deleteAll() {
        log.info("Удалены все фильмы.");
        repository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id) {
        boolean isDeleted = repository.delete(id);
        if (isDeleted) {
            log.info("Удален фильм с id = {}", id);
            return true;
        }

        log.info("Фильм с id = {} не найден.", id);
        return false;
    }
}
