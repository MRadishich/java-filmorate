package ru.yandex.practicum.filmorate.service.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dto.GenreDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreStorage genreStorage;

    @Override
    @Transactional
    public GenreDTO getById(int genreId) {
        log.info("Получен запрос на поиск жанра с id = {}", genreId);

        return genreStorage.findById(genreId)
                .map(GenreMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Жанр с id = " + genreId + " не найден."));
    }

    @Override
    @Transactional
    public List<GenreDTO> getAll() {
        log.info("Получен запрос на поиск всех жанров");

        return genreStorage.findAll().stream()
                .map(GenreMapper::toDto)
                .collect(Collectors.toList());
    }
}
