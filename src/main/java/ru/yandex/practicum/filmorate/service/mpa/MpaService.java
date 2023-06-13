package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.dto.MpaDto;

import java.util.List;

public interface MpaService {
    MpaDto getById(int id);

    List<MpaDto> getAll();
}
