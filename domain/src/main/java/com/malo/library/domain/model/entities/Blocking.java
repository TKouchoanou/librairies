package com.malo.library.domain.model.entities;

import com.malo.library.domain.model.Periodic;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Blocking extends Periodic {
    Long id;
    Long memberId;
}
