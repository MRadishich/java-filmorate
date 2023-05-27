package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.dto.GenreDTO;

import java.util.Collection;

public interface GenreService {
    GenreDTO getById(Integer id);

    Collection<GenreDTO> getAll();
}
