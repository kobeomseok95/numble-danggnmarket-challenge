package com.danggn.challenge.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = PROTECTED)
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
