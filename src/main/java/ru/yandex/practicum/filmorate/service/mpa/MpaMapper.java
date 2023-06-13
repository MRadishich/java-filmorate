package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.dto.MpaDTO;
import ru.yandex.practicum.filmorate.model.Mpa;

class MpaMapper {
    public static MpaDTO toDto(Mpa mpa) {
        return new MpaDTO(
                mpa.getId(),
                mpa.getName(),
                mpa.getDescription()
        );
    }
}
