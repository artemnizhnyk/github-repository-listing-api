package com.artemnizhnyk.githubrepositorylistingapi.exception;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ErrorDto {

    private String error;
    private String errorDescription;
}
