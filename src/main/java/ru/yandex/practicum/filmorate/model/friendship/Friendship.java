package ru.yandex.practicum.filmorate.model.friendship;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "friendship")
public class Friendship {
    @EmbeddedId
    private FriendshipId id;

    @Column(insertable = false, updatable = false)
    private Long userId;

    @Column(insertable = false, updatable = false)
    private Long friendId;

    @Enumerated(EnumType.ORDINAL)
    private FriendshipStatus status;

    public Friendship() {
    }

    public Friendship(FriendshipId id) {
        this.id = id;
    }

    public Friendship(FriendshipId id, FriendshipStatus status) {
        this.id = id;
        this.status = status;
        userId = id.getUserId();
        friendId = id.getFriendId();
    }

    public FriendshipId getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(friendId, that.friendId) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, friendId, status);
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", userId=" + userId +
                ", friendId=" + friendId +
                ", status=" + status +
                '}';
    }
}
