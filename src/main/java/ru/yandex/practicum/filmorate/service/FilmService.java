package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage storage;


    public Film create(Film film) {
        return storage.create(film);
    }

    public Collection<Film> findAll() {
        return storage.findAll();
    }

    public Film findById(long id) {
        return storage.findById(id);
    }

    public Film update(Film film) {
        return storage.update(film);
    }

    public void deleteById(long id) {
        storage.deleteById(id);
    }
}
