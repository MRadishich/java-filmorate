package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaDbStorage {
    Optional<Mpa> findById(int mpaId);

    List<Mpa> findAll();
}
