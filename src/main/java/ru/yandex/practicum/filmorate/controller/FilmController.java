package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.util.FilmValidator;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private UserService userService;
    private FilmService filmService;

    @Autowired
    public FilmController (UserService userService, FilmService filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }
    private int id = 1;


    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен PUT запрос.");
        if (FilmValidator.validate(film)) {
            filmService.getFilmStorage().updateFilm(film);
            log.info("Фильм обновлён!");
            return film;
        } else {
            log.warn("Фильм не прошёл валидацию!");
            return null;
        }
    }


    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) {
        log.info("Получен POST запрос.");
        if (FilmValidator.validate(film)) {
            film.setId(id++);
            filmService.getFilmStorage().addFilm(film);
            log.info("Фильм добавлен!");
            return film;
        } else {
            log.warn("Фильм не прошёл валидацию!");
            return null;
        }
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен GET запрос по списку фильмов.");
        return filmService.getFilmStorage().getFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmService.getFilmStorage().getFilmById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public String addLike(@PathVariable int id, @PathVariable int userId){
        log.info("Получен PUT запрос на оценку фильма");
        if (filmService.getFilmStorage().getFilmById(id) != null
                && userService.getUserStorage().getUserById(userId) != null) {
            filmService.addLike(userId,id);
            log.info("Фильм с id: " + id + " был оценён пользователем с id: " + userId );
            return "Фильм успешно получил оценку!";
        }
        else {
            return "Пользователь или фильм не найден!";
        }
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public String removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен DELETE запрос на оценку фильма");
        if (filmService.getFilmStorage().getFilmById(id) != null
                && userService.getUserStorage().getUserById(userId) != null) {
            filmService.removeLike(userId,id);
            log.info("Фильму с id: " + id + " была удалена оценка пользователем с id: " + userId );
            return "Фильму была отменена оценка!";
        }
        else {
            return "Пользователь или фильм не найден!";
        }
    }

    @GetMapping("/films/popular")
    public List<Film> getTopFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        log.info("Получен GET запрос топа фильмов");
        return filmService.getTopFilms(count);
    }
}
