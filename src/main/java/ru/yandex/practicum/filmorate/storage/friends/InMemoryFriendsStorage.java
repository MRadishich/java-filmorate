package ru.yandex.practicum.filmorate.storage.friends;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryFriendsStorage implements FriendsStorage {
    private static final Map<Long, Set<User>> friends = new HashMap<>();

    @Override
    public void createFriendList(long userId) {
        friends.put(userId, new LinkedHashSet<>());
    }

    @Override
    public Collection<User> findFriends(long userId) {
        return friends.get(userId);
    }

    @Override
    public void addFriend(User user, User friend) {
        friends.get(user.getId()).add(friend);
        friends.get(friend.getId()).add(user);
    }

    @Override
    public void deleteFriend(User user, User friend) {
        friends.get(user.getId()).remove(friend);
        friends.get(friend.getId()).remove(user);
    }
}
