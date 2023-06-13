package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MpaStorageTest {
    private final MpaStorage mpaStorage;

    @Test
    public void test1_shouldReturnMpaById() {
        Optional<Mpa> mpaOptional = mpaStorage.findById(1);

        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa -> {
                    assertThat(mpa).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(mpa).hasFieldOrPropertyWithValue("name", "G");
                    assertThat(mpa).hasFieldOrPropertyWithValue("description", "У фильма нет возрастных ограничений");
                });
    }

    @Test
    public void test2_shouldReturnAllMpa() {
        List<Mpa> mpas = mpaStorage.findAll();

        assertEquals(5, mpas.size());
    }
}
