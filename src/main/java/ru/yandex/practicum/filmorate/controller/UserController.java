package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        final List<UserDto> users = service.getAllUsers();

        return !users.isEmpty() ?
                new ResponseEntity<>(users, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable(value = "id") long userId) {
        return new ResponseEntity<>(service.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<UserDto>> findFriends(@PathVariable(value = "id") long userId) {
        return new ResponseEntity<>(service.getFriends(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<UserDto>> findCommonFriends(@PathVariable(value = "id") long userId, @PathVariable long otherId) {
        return new ResponseEntity<>(service.getCommonFriends(userId, otherId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto user) {
        return new ResponseEntity<>(service.updateUser(user), HttpStatus.OK);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable(value = "id") long userId, @PathVariable long friendId) {
        service.addFriend(userId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable(value = "id") long userId, @PathVariable long friendId) {
        service.deleteFriend(userId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") long userId) {
        service.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
