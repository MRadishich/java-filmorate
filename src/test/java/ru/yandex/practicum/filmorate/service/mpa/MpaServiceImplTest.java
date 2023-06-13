package ru.yandex.practicum.filmorate.service.mpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MpaServiceImplTest {
    @Mock
    private MpaStorage mpaStorage;
    private MpaService mpaService;

    @BeforeEach
    void setUp() {
        mpaService = new MpaServiceImpl(mpaStorage);
    }

    @Test
    public void test1_shouldReturnMpaDTOByMpaId() {
        // Given
        Mpa mpa = new Mpa(1, "G", "General Audiences");
        MpaDto expectedMpaDto = new MpaDto(1, "G", "General Audiences");

        given(mpaStorage.findById(mpa.getId())).willReturn(Optional.of(mpa));

        // When
        MpaDto foundMpaDto = mpaService.getById(mpa.getId());

        // Then
        assertEquals(expectedMpaDto, foundMpaDto);
    }

    @Test
    public void test2_shouldThrowNotFoundExceptionWhenUnknownMpaId() {
        // Given
        Mpa mpa = new Mpa(1, "G", "General Audiences");

        given(mpaStorage.findById(mpa.getId())).willReturn(Optional.empty());
        String expectedMessage = "Рейтинг Ассоциации кинокомпаний с id = " + mpa.getId() + " не найден.";

        // When
        Exception exception = assertThrows(NotFoundException.class, () -> mpaService.getById(mpa.getId()));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void test3_shouldReturnAllMpaConvertedDTO() {
        // Given
        List<Mpa> mpa = List.of(
                new Mpa(1, "G", "General Audiences"),
                new Mpa(2, "PG", "Parental guidance suggested"),
                new Mpa(3, "PG-13", "Parents strongly cautioned")
        );

        List<MpaDto> expectedMpaDtos = List.of(
                new MpaDto(1, "G", "General Audiences"),
                new MpaDto(2, "PG", "Parental guidance suggested"),
                new MpaDto(3, "PG-13", "Parents strongly cautioned")
        );

        given(mpaStorage.findAll()).willReturn(mpa);

        // When
        Collection<MpaDto> foundMpaDtos = mpaService.getAll();

        // Then
        assertEquals(expectedMpaDtos, foundMpaDtos);
    }
}