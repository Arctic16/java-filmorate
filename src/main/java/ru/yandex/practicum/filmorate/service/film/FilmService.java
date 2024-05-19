package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.util.FilmValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(int userId, int filmId) {
        if (filmStorage.getFilmById(filmId) != null) {
            filmStorage.getFilmById(filmId).getLikes().add(userId);
        }
    }

    public void removeLike(int userId, int filmId) {
        if (filmStorage.getFilmById(filmId) != null) {
            filmStorage.getFilmById(filmId).getLikes().remove(userId);
        }
    }

    public List<Film> getTopFilms(int count) {
        List<Film> films = new ArrayList<>(filmStorage.getFilms());
        films.sort(Comparator.comparingInt((Film film) -> film.getLikes().size()).reversed());
        return films.subList(0, Math.min(count, films.size()));
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

    public Film updateFilm(Film film) {
        if (FilmValidator.validate(film)) {
            return filmStorage.updateFilm(film);
        } else {
            log.warn("Фильм не прошёл валидацию!");
            return null;
        }
    }

    public Film addFilm(Film film) {
        if (FilmValidator.validate(film)) {
            return filmStorage.addFilm(film);
        } else {
            log.warn("Фильм не прошёл валидацию!");
            return null;
        }
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

}
