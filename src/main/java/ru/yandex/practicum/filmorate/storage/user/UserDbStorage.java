package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.filmorate.model.user.User;

public interface UserDbStorage extends JpaRepository<User, Long> {
}
