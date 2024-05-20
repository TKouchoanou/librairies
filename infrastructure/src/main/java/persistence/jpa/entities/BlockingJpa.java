package persistence.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BlockingJpa extends PeriodicJpa {
    @Id
    public Long id;
    public Long memberId;
}
