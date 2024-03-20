package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    HashMap<Integer, User> userHashMap = new HashMap<>();

    @PutMapping("/user")
    public User addOrUpdateUser(@RequestBody User user) throws ValidationException {
        log.info("Получен PUT запрос.");
        if (UserValidator.validate(user)) {
            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            log.info("Пользователь добавлен или обновлён");
            userHashMap.put(user.getId(), user);
            return user;
        } else {
            log.warn("Пользователь не прошёл валидацию!");
            return null;
        }
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user) throws ValidationException {
        log.info("Получен POST запрос.");
        if (UserValidator.validate(user)) {
            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            log.info("Пользователь добавлен или обновлён");
            userHashMap.put(user.getId(), user);
            return user;
        } else {
            log.warn("Пользователь не прошёл валидацию!");
            return null;
        }
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен GET запрос.");
        return new ArrayList<>(userHashMap.values());
    }


}
