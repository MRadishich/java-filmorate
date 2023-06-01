package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.dto.MpaDTO;

import java.util.List;

public interface MpaService {
    MpaDTO getById(Integer id);

    List<MpaDTO> getAll();
}
