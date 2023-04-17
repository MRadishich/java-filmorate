package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Slf4j
public class InMemoryUsersStorage implements UserStorage {
    private static final Map<Long, User> users = new HashMap<>();
    private static final AtomicInteger userId = new AtomicInteger();
    private static final String MESSAGE = "Пользователь с id = %s не найден.";

    @Override
    public User create(User user) {
        long id = userId.incrementAndGet();

        user.setId(id);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        users.put(id, user);

        log.info("Создан новый пользователь: {}", user);
        return user;
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        log.error("Запрос неизвестного пользователя c id = {}.", id);
        throw new NotFoundException(String.format(MESSAGE, id));
    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);

            log.info("Обновлен пользователь с id = {}. Новое значение: {}", user.getId(), user);
            return user;
        }

        log.error("Попытка обновить неизвестного пользователя c id = {}.", user.getId());
        throw new NotFoundException(String.format(MESSAGE, user.getId()));
    }

    @Override
    public void deleteById(long id) {
        if (users.remove(id) == null) {
            log.error("Попытка удалить неизвестного пользователя c id = {}.", id);
            throw new NotFoundException(String.format(MESSAGE, id));
        }

        log.info("Удален пользователь с id = {}.", id);
    }
}
