package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUsersStorage implements UserStorage {
    private static final Map<Long, User> users = new HashMap<>();
    private static final Map<Long, Set<User>> friends = new HashMap<>();
    private static final AtomicInteger userId = new AtomicInteger();

    @Override
    public User create(User user) {
        long id = userId.incrementAndGet();

        user.setId(id);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        users.put(id, user);
        friends.put(id, new LinkedHashSet<>());

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

    @Override
    public boolean existById(long userId) {
        return users.containsKey(userId);
    }

    @Override
    public Collection<User> findFriends(long userId) {
        if (!existById(userId)) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден.", userId));
        }

        return friends.get(userId);
    }

    @Override
    public void addFriend(long userId, long friendId) {
        if (!existById(userId)) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден.", userId));
        }

        if (!existById(friendId)) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден.", friendId));
        }

        friends.get(userId).add(users.get(friendId));
        friends.get(friendId).add(users.get(userId));
    }

    @Override
    public void deleteFriend(long userId, long friendId) {
        if (!existById(userId)) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден.", userId));
        }

        if (!existById(friendId)) {
            throw new NotFoundException(String.format("Пользователь с id = %d не найден.", friendId));
        }

        friends.get(userId).remove(users.get(friendId));
        friends.get(friendId).remove(users.get(userId));
    }
}
