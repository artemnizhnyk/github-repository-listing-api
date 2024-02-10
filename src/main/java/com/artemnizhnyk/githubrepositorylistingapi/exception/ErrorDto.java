package com.artemnizhnyk.githubrepositorylistingapi.exception;

import lombok.Builder;

@Builder
record ErrorDto(String status, String message) {
}
