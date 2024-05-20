package persistence.jpa.entities;

import domain.model.valueObject.MemberStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MemberJpa {
    @Id
    Long id;
    MemberStatus status;

}
