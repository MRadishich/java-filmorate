package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.Collection;

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
    public ResponseEntity<Collection<UserDTO>> findAll() {
        final Collection<UserDTO> users = service.findAllUsers();

        return !users.isEmpty() ?
                new ResponseEntity<>(users, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable long id) {
        return new ResponseEntity<>(service.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<Collection<UserDTO>> findFriends(@PathVariable long id) {
        return new ResponseEntity<>(service.findFriends(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<Collection<UserDTO>> findCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return new ResponseEntity<>(service.findCommonFriends(id, otherId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO user) {
        return new ResponseEntity<>(service.updateUser(user), HttpStatus.OK);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable long id, @PathVariable long friendId) {
        service.addFriend(id, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        service.deleteFriend(id, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        service.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
