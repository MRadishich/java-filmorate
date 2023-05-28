package ru.yandex.practicum.filmorate.model.friendship;

public enum FriendshipStatus {
    NOTAPPROVED(0),
    APPROVED(1);

    private final int value;

    FriendshipStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FriendshipStatus getStatus(int value) {
        for (FriendshipStatus status : FriendshipStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }

        return null;
    }

}
