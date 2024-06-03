package com.malo.library.persistence.jpa.entities.abstractEntity;

import com.malo.library.persistence.jpa.Versioned;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VersionedJpa extends AuditableJpa implements Versioned {
    private Long version;
}
