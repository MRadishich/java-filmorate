package ru.yandex.practicum.filmorate.service.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.function.Function;

@Component
public class FilmFromDTOMapper implements Function<FilmDTO, Film> {
    @Override
    public Film apply(FilmDTO filmDTO) {
        return new Film().toBuilder()
                .id(filmDTO.getId())
                .name(filmDTO.getName())
                .releaseDate(filmDTO.getReleaseDate())
                .description(filmDTO.getDescription())
                .duration(filmDTO.getDuration())
                .mpaId(filmDTO.getMpa() == null ? null : filmDTO.getMpa().getId())
                .genres(filmDTO.getGenres())
                .build();
    }
}
