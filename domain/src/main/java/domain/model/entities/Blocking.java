package domain.model.entities;

import domain.model.Periodic;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Blocking extends Periodic {
    Long id;
    Long memberId;
}
