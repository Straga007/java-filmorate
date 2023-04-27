package ru.yandex.practicum.filmorate.controller;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
public class UserController {
    private final List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            users.add(user);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping("/users")
    public User updateUser(@RequestBody User userToUpdate) {
        User existingUser = users.stream()
                .filter(u -> u.getId() == userToUpdate.getId())
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No user with id " + userToUpdate.getId()));

        String email = userToUpdate.getEmail();
        String login = userToUpdate.getLogin();
        String name = userToUpdate.getName();
        LocalDate birthday = userToUpdate.getBirthday();

        if (email != null && !email.trim().isEmpty()) {
            if (!email.contains("@")) {
                throw new IllegalArgumentException("Email must contain @ symbol");
            }
            existingUser.setEmail(email.trim());
        }

        if (login != null && !login.trim().isEmpty()) {
            if (login.contains(" ")) {
                throw new IllegalArgumentException("Login must not contain spaces");
            }
            existingUser.setLogin(login.trim());
        }

        if (name != null) {
            existingUser.setName(name.trim().isEmpty() ? existingUser.getLogin() : name.trim());
        }

        if (birthday != null) {
            if (birthday.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Date of birth cannot be in the future");
            }
            existingUser.setBirthday(birthday);
        }

        log.info("Updating user with id {}: {}", userToUpdate.getId(), existingUser);

        return existingUser;
    }


    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        User userToDelete = null;
        for (User user : users) {
            if (user.getId() == id) {
                userToDelete = user;
                break;
            }
        }
        if (userToDelete != null) {
            users.remove(userToDelete);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return users;
    }

}
