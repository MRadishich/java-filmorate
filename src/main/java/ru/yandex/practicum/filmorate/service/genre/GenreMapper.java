package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.model.Genre;

class GenreMapper {
    public static GenreDTO toDto(Genre genre) {
        return new GenreDTO(
                genre.getId(),
                genre.getName()
        );
    }
}
