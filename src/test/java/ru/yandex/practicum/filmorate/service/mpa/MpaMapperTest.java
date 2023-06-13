package ru.yandex.practicum.filmorate.service.mpa;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MpaMapperTest {
    @Test
    void test1_shouldConvertedMpaToMpaDTO() {
        // Given
        Mpa mpa = new Mpa(1, "G", "General Audiences");
        MpaDto expectedMpaDto = new MpaDto(1, "G", "General Audiences");

        // When
        MpaDto convertedMpaDto = MpaMapper.toDto(mpa);

        //
        assertEquals(expectedMpaDto, convertedMpaDto);
    }
}