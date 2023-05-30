package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.dto.MpaDTO;

import java.util.Collection;

public interface MpaService {
    MpaDTO getById(Integer id);

    Collection<MpaDTO> getAll();
}
