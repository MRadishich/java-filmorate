package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private final List<String> error;
}
