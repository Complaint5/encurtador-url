package com.complaint5.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record UrlUpdateDto(UUID id, @NotBlank String originalUrl) {
}
