package persistence.jpa.entities;

import domain.model.valueObject.MemberAccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MemberJpa {
    @Id
    Long id;
    MemberAccountStatus status;
    @OneToMany
    List<BlockingJpa> currentBlockings;

}
