package com.artemnizhnyk.githubrepositorylistingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
class CustomExceptionController {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    ResponseEntity<ErrorDto> handleNotFoundException(HttpClientErrorException.NotFound e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .status(HttpStatus.NOT_FOUND.toString())
                        .message("Any repositories weren't found with entered username")
                        .build());
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    ResponseEntity<ErrorDto> handleNotFoundException(HttpClientErrorException.Forbidden e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorDto.builder()
                        .status(HttpStatus.FORBIDDEN.toString())
                        .message("You should send your auth token as request param, " +
                                "only 60 per hour requests is available without authorization")
                        .build());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    ResponseEntity<ErrorDto> handleNotFoundException(HttpClientErrorException.Unauthorized e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorDto.builder()
                        .status(HttpStatus.UNAUTHORIZED.toString())
                        .message("Wrong auth token")
                        .build());
    }
}
