package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;


import java.util.*;

@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("user/{id}/friends/{friendId}") // добавление в друзья
    public void addFriend(@PathVariable("id") int userId, @PathVariable("friendId") int friendId) {
        userService.addFriend(userId, friendId);
        log.info("Пользователь c id {} добавил в друзья пользователя с id {}", userId, friendId);
    }

    @GetMapping("user/{id}/friends")
    public Set<Integer> getAllFriend(@PathVariable int id) {
        return userService.getAllFriend(id);
    }


}
