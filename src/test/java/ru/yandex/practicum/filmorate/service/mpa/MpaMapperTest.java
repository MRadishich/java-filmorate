package ru.yandex.practicum.filmorate.service.mpa;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.MpaDTO;
import ru.yandex.practicum.filmorate.model.Mpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MpaMapperTest {
    @Test
    void test1_shouldConvertedMpaToMpaDTO() {
        // Given
        Mpa mpa = new Mpa(1, "G", "General Audiences");
        MpaDTO expectedMpaDTO = new MpaDTO(1, "G", "General Audiences");

        // When
        MpaDTO convertedMpaDTO = MpaMapper.toDto(mpa);

        //
        assertEquals(expectedMpaDTO, convertedMpaDTO);
    }
}