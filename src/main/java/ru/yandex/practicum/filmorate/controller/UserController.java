package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
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
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId() == userToUpdate.getId()) {

                if (userToUpdate.getEmail() != null && userToUpdate.getEmail().trim().length() > 0) {
                    if (!userToUpdate.getEmail().contains("@")) {
                        throw new IllegalArgumentException("Email must contain @ symbol");
                    }
                    user.setEmail(userToUpdate.getEmail().trim());
                }

                if (userToUpdate.getLogin() != null && userToUpdate.getLogin().trim().length() > 0) {
                    if (userToUpdate.getLogin().contains(" ")) {
                        throw new IllegalArgumentException("Login must not contain spaces");
                    }
                    user.setLogin(userToUpdate.getLogin().trim());
                }

                if (userToUpdate.getName() != null) {
                    user.setName(userToUpdate.getName().trim().length() > 0 ? userToUpdate.getName().trim() : userToUpdate.getLogin());
                }

                if (userToUpdate.getBirthday() != null) {
                    if (userToUpdate.getBirthday().isAfter(LocalDate.now())) {
                        throw new IllegalArgumentException("Date of birth cannot be in the future");
                    }
                    user.setBirthday(userToUpdate.getBirthday());
                }

                return user;
            }
        }
        throw new NoSuchElementException("User with id " + userToUpdate.getId() + " not found");
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
