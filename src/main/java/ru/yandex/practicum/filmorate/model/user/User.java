package ru.yandex.practicum.filmorate.model.user;

import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class User {
    private Long id;

    private String email;

    private String login;

    private String name;

    private LocalDate birthday;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("login", login);
        map.put("name", name);
        map.put("birthday", birthday);

        return map;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(login, user.login) && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, login, name, birthday);
    }
}
