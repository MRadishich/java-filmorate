package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.function.Function;

@Component
class GenreDTOMapper implements Function<Genre, GenreDTO> {
    @Override
    public GenreDTO apply(Genre genre) {
        return new GenreDTO(
                genre.getId(),
                genre.getName()
        );
    }
}
