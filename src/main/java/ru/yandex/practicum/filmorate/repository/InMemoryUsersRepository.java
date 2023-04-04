package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUsersRepository implements UserRepository {
    private static final Map<Integer, User> users = new HashMap<>();
    private static final AtomicInteger userId = new AtomicInteger();

    @Override
    public int create(User user) {
        int id = userId.incrementAndGet();
        user.setId(id);
        users.put(id, user);

        return id;
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User get(int id) {
        return users.get(id);
    }

    @Override
    public boolean update(int id, User user) {
        if (users.containsKey(id)) {
            user.setId(id);
            users.put(id, user);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        return users.remove(id) != null;
    }
}
