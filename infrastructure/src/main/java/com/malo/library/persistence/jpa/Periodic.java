package com.malo.library.persistence.jpa;

import java.time.LocalDateTime;

public interface Periodic {
    LocalDateTime getStartDate();
    void setStartDate(LocalDateTime startDate);
    LocalDateTime getEndDate();
    void setEndDate(LocalDateTime endDate);
}
