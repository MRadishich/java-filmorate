package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@RequiredArgsConstructor
public class InMemoryUsersStorage implements UserStorage {
    private static final Map<Long, User> users = new HashMap<>();
    private static final AtomicInteger userId = new AtomicInteger();

    @Override
    public User create(User user) {
        long id = userId.incrementAndGet();

        user.setId(id);

        setNameIfNull(user);

        users.put(id, user);

        return user;
    }

    private void setNameIfNull(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(long userId) {
        User user = users.get(userId);

        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id = %s не найден.", userId));
        }

        return user;
    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        }

        throw new NotFoundException(String.format(String.format("Пользователь с id = %s не найден.", user.getId())));
    }

    @Override
    public void deleteById(long userId) {
        if (users.remove(userId) == null) {
            throw new NotFoundException(String.format("Пользователь с userId = %s не найден.", userId));
        }
    }
}
