package com.malo.library.jpa.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class PeriodicJpa {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
