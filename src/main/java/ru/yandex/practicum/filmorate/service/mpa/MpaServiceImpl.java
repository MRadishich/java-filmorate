package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dto.MpaDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final MpaStorage mpaStorage;

    @Override
    @Transactional
    public MpaDTO getById(int mpaId) {
        return mpaStorage.findById(mpaId)
                .map(MpaMapper::toDto)
                .orElseThrow(() -> new NotFoundException(
                        "Рейтинг Ассоциации кинокомпаний с id = " + mpaId + " не найден."));
    }

    @Override
    @Transactional
    public List<MpaDTO> getAll() {
        return mpaStorage.findAll().stream()
                .map(MpaMapper::toDto)
                .collect(Collectors.toList());
    }

}
