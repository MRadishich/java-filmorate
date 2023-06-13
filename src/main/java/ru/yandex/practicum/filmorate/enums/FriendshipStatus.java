package ru.yandex.practicum.filmorate.enums;

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
}
