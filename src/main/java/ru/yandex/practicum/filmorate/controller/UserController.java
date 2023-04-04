package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        int userId = repository.create(user);

        log.info("Создан новый пользователь: {}", user);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/users/" + userId);

        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAll() {
        final Collection<User> users = repository.getAll();

        return !users.isEmpty() ?
                new ResponseEntity<>(users, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable(name = "id") int id) {
        final User user = repository.get(id);

        return user != null ?
                new ResponseEntity<>(user, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@Valid @PathVariable(name = "id") int id, @RequestBody User user) {
        boolean isUpdated = repository.update(id, user);

        if (isUpdated) {
            log.info("Обновлен пользователь с id = {}. Новое значение: {}", id, user);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        log.info("Пользователь с id = {} не найден. Обновление не возможно.", id);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable(name = "id") int id) {
        boolean isDeleted = repository.delete(id);

        if (isDeleted) {
            log.info("Пользователь с id = {} удален.", id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        log.info("Пользователь с id = {} не найден. Удаление не возможно.", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
