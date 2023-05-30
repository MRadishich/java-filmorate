package ru.yandex.practicum.filmorate.model.like;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Like {
    private Long filmId;
    private Long userId;
}
