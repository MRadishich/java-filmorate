package ru.yandex.practicum.filmorate.storage.friends;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryFriendStorage implements FriendStorage {
    private static final Map<Long, Set<User>> friends = new HashMap<>();

    @Override
    public Collection<User> findFriends(long userId) {
        Collection<User> userFriends = friends.get(userId);
        return friends.get(userId) == null ? List.of() : userFriends;
    }

    @Override
    public void addFriend(User user, User friend) {
        if (friends.containsKey(user.getId())) {
            friends.get(user.getId()).add(friend);
        } else {
            friends.put(user.getId(), new LinkedHashSet<>());
            friends.get(user.getId()).add(friend);
        }

        if (friends.containsKey(friend.getId())) {
            friends.get(friend.getId()).add(user);
        } else {
            friends.put(friend.getId(), new LinkedHashSet<>());
            friends.get(friend.getId()).add(user);
        }
    }

    @Override
    public void deleteFriend(User user, User friend) {
        friends.get(user.getId()).remove(friend);
        friends.get(friend.getId()).remove(user);
    }
}
