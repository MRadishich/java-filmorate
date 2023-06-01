package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    GenreDTO getById(Integer id);

    List<GenreDTO> getAll();
}
