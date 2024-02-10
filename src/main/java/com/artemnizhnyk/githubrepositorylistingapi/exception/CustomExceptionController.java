package com.artemnizhnyk.githubrepositorylistingapi.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;


@RequiredArgsConstructor
@RestControllerAdvice
public class CustomExceptionController {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(HttpClientErrorException.NotFound e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .error(HttpStatus.NOT_FOUND.toString())
                        .errorDescription("Any repositories weren't found with entered username")
                        .build());
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(HttpClientErrorException.Forbidden e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorDto.builder()
                        .error(HttpStatus.FORBIDDEN.toString())
                        .errorDescription("You should send your auth token as request param, " +
                                "only 60 per hour requests is available without authorization")
                        .build());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(HttpClientErrorException.Unauthorized e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorDto.builder()
                        .error(HttpStatus.UNAUTHORIZED.toString())
                        .errorDescription("Wrong auth token")
                        .build());
    }
}
