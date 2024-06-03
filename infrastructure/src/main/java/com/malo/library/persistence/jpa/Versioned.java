package com.malo.library.persistence.jpa;

public interface Versioned {
    Long getVersion();
    void setVersion(Long version);
}
