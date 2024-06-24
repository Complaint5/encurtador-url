package com.complaint5.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.complaint5.Entities.UrlEntity;
import com.complaint5.dtos.UrlResponseDto;
import com.complaint5.dtos.UrlUpdateDto;
import com.complaint5.services.UrlService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping()
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/url")
    public ResponseEntity<UrlResponseDto> encurtar(@RequestBody @Valid UrlResponseDto urlResponseDto,
            UriComponentsBuilder uriBuilder) {
        return ResponseEntity.created(uriBuilder.path("/" + this.urlService.saveUrl(urlResponseDto)).build().toUri())
                .body(new UrlResponseDto(uriBuilder.build().toString()));
    }

    @GetMapping("/url")
    public ResponseEntity<List<UrlEntity>> getAllUrl() {
        return ResponseEntity.ok(this.urlService.findAllUrl());
    }

    @GetMapping("/url/{id}")
    public ResponseEntity<UrlEntity> getUrlById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(this.urlService.findUrlById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> redirect(@PathVariable("id") String url, HttpServletResponse response) {
        String newUrl = this.urlService.redirect(url);

        try {
            response.sendRedirect(newUrl);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/url/{id}")
    public ResponseEntity<UrlEntity> updateUrl(@PathVariable UUID id, @RequestBody @Valid UrlUpdateDto url) {
        return ResponseEntity.ok(this.urlService.updateUrl(url, id));
    }
}
