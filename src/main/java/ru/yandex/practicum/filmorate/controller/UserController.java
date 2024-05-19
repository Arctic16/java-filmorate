package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.user.UserService;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private UserService userService;
    private FilmService filmService;

    @Autowired
    public UserController(UserService userService, FilmService filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("Получен PUT запрос.");
        return userService.updateUser(user);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        log.info("Получен POST запрос.");
        return userService.addUser(user);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен GET запрос по списку пользователей.");
        return userService.getUsers();
    }

    @GetMapping("users/{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("Был получен GET запрос пользователя по id.");
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Был получен запрос на добавление в друзья");
        userService.addFriends(id, friendId);
        return getUserById(id);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User deleteFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Был получен запрос на удаление из друзей");
        userService.removeFriends(id, friendId);
        return getUserById(id);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        log.info("Получен GET запрос списка друзей.");
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Получен GET запрос на список общих друзей");
        return userService.getCommonFriends(id, otherId);
    }
}
