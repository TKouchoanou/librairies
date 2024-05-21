package com.malo.library.domain.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Getter
@SuperBuilder
public abstract class Periodic {
    LocalDateTime startDate;

    LocalDateTime endDate;
    public boolean coversToday() {
        LocalDateTime now = LocalDateTime.now();
        return (now.isEqual(startDate) || now.isAfter(startDate)) && (now.isEqual(endDate) || now.isBefore(endDate));
    }


}
