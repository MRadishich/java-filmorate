package ru.yandex.practicum.filmorate.service.mpa;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.MpaDTO;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.function.Function;

@Component
class MpaDTOMapper implements Function<Mpa, MpaDTO> {
    @Override
    public MpaDTO apply(Mpa mpa) {
        return new MpaDTO(
                mpa.getId(),
                mpa.getName(),
                mpa.getDescription()
        );
    }
}
