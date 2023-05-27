package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Mpa;

@Repository
public interface MpaDbStorage extends JpaRepository<Mpa, Integer> {
}
