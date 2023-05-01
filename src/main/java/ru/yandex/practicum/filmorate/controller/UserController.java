package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            validate(user);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        }
        User newUser = new User(user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        users.put(newUser.getId(), newUser);
        return ResponseEntity.ok(newUser);

    }


    @PutMapping("/users")
    public User updateUser(@RequestBody User userToUpdate) {
        User existingUser = users.get(userToUpdate.getId());

        try {
            validate(userToUpdate);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        }
        if (userToUpdate.getName() != null) {
            existingUser.setName(userToUpdate.getName().trim().isEmpty() ? existingUser.getLogin() : userToUpdate.getName().trim());
        }
        existingUser.setEmail(userToUpdate.getEmail());
        existingUser.setBirthday(userToUpdate.getBirthday());
        existingUser.setId(userToUpdate.getId());
        existingUser.setLogin(userToUpdate.getLogin());
        existingUser.setName(userToUpdate.getName());


        log.info("Updating user with id {}: {}", userToUpdate.getId(), existingUser);

        return existingUser;
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        User userToDelete = users.get(id);
        if (userToDelete != null) {
            users.remove(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void validate(User user) throws IllegalArgumentException {
        String email = user.getEmail();
        String login = user.getLogin();
        LocalDate birthday = user.getBirthday();

        if (email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain @ symbol");
        }
        if (login.trim().isEmpty() || login.contains(" ")) {
            throw new IllegalArgumentException("Login must not contain spaces");
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        log.info("Текущее количество постов: {}", users.size());
        return new ArrayList<>(users.values());
    }

}
