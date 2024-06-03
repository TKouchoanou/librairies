package com.malo.library.persistence.jpa.entities.abstractEntity;



import com.malo.library.persistence.jpa.Auditable;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditableJpa implements Auditable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String createdBy;
    private String lastModifiedBy;
}
