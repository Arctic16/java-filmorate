package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.user.UserService;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private UserService userService;
    private FilmService filmService;

    @Autowired
    public FilmController(UserService userService, FilmService filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен PUT запрос.");
        return filmService.updateFilm(film);
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) {
        log.info("Получен POST запрос.");
        return filmService.addFilm(film);
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен GET запрос по списку фильмов.");
        return filmService.getFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public String addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен PUT запрос на оценку фильма");
        filmService.addLike(userService.getUserById(userId).getId(), id);
        log.info("Фильм с id: " + id + " был оценён пользователем с id: " + userId);

        return "Фильм успешно получил оценку!";
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public String removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен DELETE запрос на оценку фильма");
        filmService.removeLike(userService.getUserById(userId).getId(), id);
        log.info("Фильму с id: " + id + " была удалена оценка пользователем с id: " + userId);

        return "Фильму была удалена оценка!";
    }

    @GetMapping("/films/popular")
    public List<Film> getTopFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        log.info("Получен GET запрос топа фильмов");
        return filmService.getTopFilms(count);
    }
}
