package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryLikeStorage implements LikeStorage {
    private static final Map<Long, Set<Long>> likes = new HashMap<>();

    @Override
    public void create(long filmId) {
        likes.put(filmId, new HashSet<>());
    }

    @Override
    public void addLike(long filmId, long userId) {
        likes.get(filmId).add(userId);
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        if (!likes.get(filmId).remove(userId)) {
            throw new NotFoundException(String.format("Лайк от пользователя с id = %d не найден.", userId));
        }
    }

    @Override
    public Collection<Long> findPopular(long count) {
        return likes.entrySet().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getValue().size(), f1.getValue().size()))
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
