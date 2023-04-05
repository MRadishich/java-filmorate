package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUsersRepository implements UserRepository {
    private static final Map<Integer, User> users = new HashMap<>();
    private static final AtomicInteger userId = new AtomicInteger();
    private static final String MESSAGE = "Пользователь с id = %s не найден.";

    @Override
    public User create(User user) {
        int id = userId.incrementAndGet();
        user.setId(id);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        users.put(id, user);

        return user;
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User get(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }

        throw new NotFoundException(String.format(MESSAGE, id));
    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        }

        throw new NotFoundException(String.format(MESSAGE, user.getId()));
    }

    @Override
    public boolean delete(int id) {
        if (users.remove(id) != null) {
            return true;
        }

        throw new NotFoundException(String.format(MESSAGE, id));
    }
}
