package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.util.UserValidator;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class UserController {
    private UserService userService;
    private FilmService filmService;
    private int id = 1;

    @Autowired
    public UserController(UserService userService, FilmService filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("Получен PUT запрос.");
        if (UserValidator.validate(user)) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            } else if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            userService.getUserStorage().updateUser(user);
            log.info("Пользователь обновлён");
            return user;
        } else {
            log.warn("Пользователь не прошёл валидацию или его не существует чтобы обновить!");
            return null;
        }
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        log.info("Получен POST запрос.");
        if (UserValidator.validate(user)) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            } else if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }

            log.info("Пользователь добавлен");
            user.setId(id++);
            userService.getUserStorage().addUser(user);
            return user;
        } else {
            log.warn("Пользователь не прошёл валидацию!");
            return null;
        }
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен GET запрос по списку пользователей.");
        return userService.getUserStorage().getUsers();
    }

    @GetMapping("user/{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("Был получен GET запрос пользователя по id.");
        return userService.getUserStorage().getUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public String addFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Был получен запрос на добавление в друзья");
        userService.addFriends(id, friendId);
        return "При наличии данных пользователей они были добавлены в друзья.";
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public String deleteFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Был получен запрос на удаление из друзей");
        userService.removeFriends(id, friendId);
        return "При наличии данных пользователей они были удалены из друзей.";
    }

    @GetMapping("/users/{id}/friends")
    public Set<Integer> getFriends(@PathVariable Integer id) {
        log.info("Получен GET запрос списка друзей");
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<Integer> getCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Получен GET запрос на список общих друзей");
        return userService.getCommonFriends(id, otherId);
    }
}
