package com.malo.library.exception.technical;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TechnicalExceptionKey {
    ENTITY_NOT_FOUND(500, ""),
    EMPTY_CONDITION_IN_FIND_BY_FOREIGN_KEY(500, "la condition ne doit pas être vide");
    private final int code;
    private final String message;
}
