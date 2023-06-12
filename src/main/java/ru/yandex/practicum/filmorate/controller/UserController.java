package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        final List<UserDTO> users = service.getAllUsers();

        return !users.isEmpty() ?
                new ResponseEntity<>(users, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(value = "id") long userId) {
        return new ResponseEntity<>(service.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<UserDTO>> findFriends(@PathVariable(value = "id") long userId) {
        return new ResponseEntity<>(service.getFriends(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<UserDTO>> findCommonFriends(@PathVariable(value = "id") long userId, @PathVariable long otherId) {
        return new ResponseEntity<>(service.getCommonFriends(userId, otherId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO user) {
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
