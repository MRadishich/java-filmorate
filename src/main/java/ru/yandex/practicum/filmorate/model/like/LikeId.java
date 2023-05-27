package ru.yandex.practicum.filmorate.model.like;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable {
    private Long filmId;
    private Long userId;
}
