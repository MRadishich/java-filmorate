package ru.yandex.practicum.filmorate.model.like;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity(name = "likes")
public class Like {
    @EmbeddedId
    private LikeId likeId;

    @Column(insertable = false, updatable = false)
    private Long filmId;

    @Column(insertable = false, updatable = false)
    private Long userId;

    public Like() {
    }

    public Like(LikeId likeId) {
        this.likeId = likeId;
        this.filmId = likeId.getFilmId();
        this.userId = likeId.getUserId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Like like = (Like) o;
        return likeId != null && Objects.equals(likeId, like.likeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likeId);
    }

    @Override
    public String toString() {
        return "Like{" +
                "likeId=" + likeId +
                ", filmId=" + filmId +
                ", userId=" + userId +
                '}';
    }
}
