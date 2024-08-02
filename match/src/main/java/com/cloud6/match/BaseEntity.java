package com.cloud6.match;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
public abstract class BaseEntity {
    private Boolean isSuccess;
    private String code;
    private String message;
}
