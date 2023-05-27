package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.MpaDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final MpaDbStorage mpaStorage;
    private final MpaDTOMapper mpaDTOMapper;

    public MpaDTO getById(Integer mpaId) {
        return mpaStorage.findById(mpaId)
                .map(mpaDTOMapper)
                .orElseThrow(() -> new NotFoundException(
                        "Рейтинг Ассоциации кинокомпаний с id = " + mpaId + " не найден."));
    }

    @Override
    public Collection<MpaDTO> getAll() {
        return mpaStorage.findAll().stream()
                .map(mpaDTOMapper)
                .collect(Collectors.toList());
    }

}
