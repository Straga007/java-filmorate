package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {
    private final List<User> users = new ArrayList<>();

    @PostMapping("/registration")
    public User createUser(@RequestBody User user) {
        users.add(user);
        return user;
    }

    @PutMapping("/users/user/status/update/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User userToUpdate) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId() == id) {

                if (userToUpdate.getEmail() != null) {
                    user.setEmail(userToUpdate.getEmail());
                }
                if (userToUpdate.getLogin() != null) {
                    user.setLogin(userToUpdate.getLogin());
                }
                if (userToUpdate.getName() != null) {
                    user.setName(userToUpdate.getName());
                }
                return user;
            }
        }
        throw new NoSuchElementException("User with id " + id + " not found");
    }

    @DeleteMapping("/users/user/status/delete/{id}")
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
