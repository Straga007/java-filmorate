package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.status.FriendshipStatus;

@Slf4j
@Data
@NonNull
@ToString
public class User {

    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    public Map<Integer, FriendshipStatus> friends;
    private Set<Integer> pendingFriends;


    public User(String email, @NonNull String login, String name, @NonNull LocalDate birthday, int id) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name == null || name.trim().isEmpty() ? login : name;
        this.birthday = birthday;
        this.friends = new HashMap<>();
        this.pendingFriends = new HashSet<>();
    }

    public void setFriend(int id, FriendshipStatus status) {
        friends.put(id, status);
    }

    public void delFriend(int id) {
        friends.remove(id);
    }

    public Set<Integer> getFriends() {
        return friends.keySet();
    }

    public void addPendingFriend(int friendId) {
        pendingFriends.add(friendId);
    }

    public void removePendingFriend(int friendId) {
        pendingFriends.remove(friendId);
    }

    public Set<Integer> getPendingFriends() {
        return pendingFriends;
    }
}
