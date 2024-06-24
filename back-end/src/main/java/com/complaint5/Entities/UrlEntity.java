package com.complaint5.Entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "url")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UrlEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 1000)
    private String originalUrl;
    private String shortened;
    private Long accessNumber;
    private LocalDateTime lastAccess;
    private LocalDateTime creatData;

    public UrlEntity(){
        this.accessNumber = 0L;
        this.creatData = LocalDateTime.now();
    }
}
