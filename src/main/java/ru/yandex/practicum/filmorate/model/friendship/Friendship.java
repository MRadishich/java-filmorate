package ru.yandex.practicum.filmorate.model.friendship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Friendship {
    private Long userId;
    private Long friendId;
    private FriendshipStatus status;

    public Friendship(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}
