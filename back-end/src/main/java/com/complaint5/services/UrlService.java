package com.complaint5.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint5.Entities.UrlEntity;
import com.complaint5.dtos.UrlResponseDto;
import com.complaint5.dtos.UrlUpdateDto;
import com.complaint5.exceptions.IdsDoNotMatchException;
import com.complaint5.exceptions.UrlNotFoundExcecption;
import com.complaint5.repositories.UrlRepository;

import jakarta.transaction.Transactional;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    @Transactional
    public String redirect(String shortened){
        UrlEntity url = this.getUrlByShortened(shortened).orElseThrow(() -> new UrlNotFoundExcecption());

        url.setLastAccess(LocalDateTime.now());
        url.setAccessNumber(url.getAccessNumber() + 1);

        return this.urlRepository.save(url).getOriginalUrl();
    }

    @Transactional
    public String saveUrl(UrlResponseDto urlResponseDto) {
        Optional<UrlEntity> url = this.getByOriginalUrl(urlResponseDto.url());

        if (url.isPresent()){
            return url.get().getShortened();
        }
        
        UrlEntity newUrl = new UrlEntity();
        newUrl.setOriginalUrl(urlResponseDto.url());
        newUrl.setShortened(this.createUrl());
        
        this.urlRepository.save(newUrl);

        return this.urlRepository.save(newUrl).getShortened();
    }

    @Transactional
    public UrlEntity updateUrl(UrlUpdateDto url, UUID id){
        UrlEntity updateUrl = this.findUrlById(id);

        if (!updateUrl.getId().equals(url.id())){
            throw new IdsDoNotMatchException();
        }

        updateUrl.setOriginalUrl(url.originalUrl());

        return this.urlRepository.save(updateUrl);
    }

    public List<UrlEntity> findAllUrl(){
        return this.urlRepository.findAll();
    }

    public UrlEntity findUrlById(UUID id){
        return this.urlRepository.findById(id).orElseThrow(() -> new UrlNotFoundExcecption());
    }

    private String createUrl() {
        String url = "";

        do {
            url = createUrlShortened();
        } while (getUrlByShortened(url).isPresent());

        return url;
    }

    private String createUrlShortened() {
        String url = "";

        for (int i = 0; i < (int) (Math.random() * 6) + 5; i++) {
            url += CHARACTERS.charAt((int) (Math.random() * CHARACTERS.length()));
        }

        return url;
    }

    private Optional<UrlEntity> getUrlByShortened(String url) {
        return this.urlRepository.findByShortened(url);
    }

    private Optional<UrlEntity> getByOriginalUrl(String url){
        return this.urlRepository.findByOriginalUrl(url);
    }
}
