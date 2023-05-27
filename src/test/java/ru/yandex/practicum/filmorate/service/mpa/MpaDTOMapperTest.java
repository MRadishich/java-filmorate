package ru.yandex.practicum.filmorate.service.mpa;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.MpaDTO;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import static org.junit.jupiter.api.Assertions.*;

class MpaDTOMapperTest {
    private final MpaDTOMapper mpaDTOMapper = new MpaDTOMapper();

    @Test
    void test1_shouldConvertedMpaToMpaDTO() {
        // Given
        Mpa mpa = new Mpa(1, "G", "General Audiences");
        MpaDTO expectedMpaDTO = new MpaDTO(1, "G", "General Audiences");

        // When
        MpaDTO convertedMpaDTO = mpaDTOMapper.apply(mpa);

        //
        assertEquals(expectedMpaDTO, convertedMpaDTO);
    }
}