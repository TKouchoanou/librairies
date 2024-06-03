package com.malo.library.persistence.jpa.entities;

import com.malo.library.persistence.jpa.Periodic;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class PeriodicJpa extends AuditableJpa implements Periodic {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
