package ru.yandex.practicum.filmorate.service.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreStorage genreStorage;

    @Override
    public GenreDTO getById(Integer genreId) {
        log.info("Получен запрос на поиск жанра с id = {}", genreId);

        return genreStorage.findById(genreId)
                .map(GenreMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Жанр с id = " + genreId + " не найден."));
    }

    @Override
    public Collection<GenreDTO> getAll() {
        log.info("Получен запрос на поиск всех жанров");

        return genreStorage.findAll().stream()
                .map(GenreMapper::toDto)
                .collect(Collectors.toList());
    }
}
