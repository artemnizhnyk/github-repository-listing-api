package com.artemnizhnyk.githubrepositorylistingapi.exception;

import lombok.Builder;

@Builder
public record ErrorDto(String status, String message) {
}
