package com.complaint5.dtos;

import jakarta.validation.constraints.NotBlank;

public record UrlResponseDto(@NotBlank String url) {
    
}
