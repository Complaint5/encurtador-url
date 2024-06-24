package com.complaint5.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.complaint5.Entities.UrlEntity;

public interface UrlRepository extends JpaRepository<UrlEntity, UUID> {
    Optional<UrlEntity> findByShortened(String url);

    Optional<UrlEntity> findByOriginalUrl(String url);
}
