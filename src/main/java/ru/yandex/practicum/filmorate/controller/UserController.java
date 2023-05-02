package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private static int nextId = 0;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        validate(user);
        int id = ++nextId;
        User newUser = new User(user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), id);
        users.put(newUser.getId(), newUser);
        return ResponseEntity.ok(newUser);

    }


    @PutMapping("/users")
    public User updateUser(@RequestBody User userToUpdate) {
        User existingUser = users.get(userToUpdate.getId());
        if (existingUser == null) {
            throw new InternalServerException("User with id " + userToUpdate.getId() + " not found");
        }
        validate(userToUpdate);

        existingUser.setEmail(userToUpdate.getEmail());
        existingUser.setBirthday(userToUpdate.getBirthday());
        existingUser.setLogin(userToUpdate.getLogin());
        existingUser.setName(userToUpdate.getName());

        log.info("Updating user with id {}: {}", userToUpdate.getId(), existingUser);

        return existingUser;
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (users.remove(id) != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void validate(User user) {
        String email = user.getEmail();
        String login = user.getLogin();
        LocalDate birthday = user.getBirthday();

        if (email.trim().isEmpty() || !email.contains("@")) {
            throw new ValidationException("Email must contain @ symbol");
        }
        if (login.trim().isEmpty() || login.contains(" ")) {
            throw new ValidationException("Login must not contain spaces");
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new ValidationException("Date of birth cannot be in the future");
        }
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        log.info("Текущее количество постов: {}", users.size());
        return new ArrayList<>(users.values());
    }

}
