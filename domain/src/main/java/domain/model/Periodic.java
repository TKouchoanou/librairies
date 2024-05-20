package domain.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Getter
@SuperBuilder
public abstract class Periodic {
    LocalDateTime startDate;

    LocalDateTime endDate;
}
