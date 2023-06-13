package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.dto.GenreDto;

import java.util.List;

public interface GenreService {
    GenreDto getById(int id);

    List<GenreDto> getAll();
}
