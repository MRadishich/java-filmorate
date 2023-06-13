package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

class MpaMapper {
    public static MpaDto toDto(Mpa mpa) {
        return new MpaDto(
                mpa.getId(),
                mpa.getName(),
                mpa.getDescription()
        );
    }
}
