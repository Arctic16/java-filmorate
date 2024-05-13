package ru.yandex.practicum.filmorate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.SearchException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;


@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleValidationException(final ValidationException ex) {
        log.error("Получен код 400");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(ex.getMessage()));
    }

    @ExceptionHandler(SearchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleSearchException(SearchException ex) {
        log.error("Получен код 404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception ex) {
        log.error("Получен код 500");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error(ex.getMessage()));
    }
}