package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friends.FriendsStorage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@RequiredArgsConstructor
public class InMemoryUsersStorage implements UserStorage {
    private static final Map<Long, User> users = new HashMap<>();
    private static final AtomicInteger userId = new AtomicInteger();

    private final FriendsStorage friendsStorage;

    @Override
    public User create(User user) {
        long id = userId.incrementAndGet();

        user.setId(id);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        users.put(id, user);
        friendsStorage.createFriendList(user.getId());

        return user;
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
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
    public void deleteById(long id) {
        if (users.remove(id) == null) {
            throw new NotFoundException(String.format("Пользователь с id = %s не найден.", id));
        }
    }
}
