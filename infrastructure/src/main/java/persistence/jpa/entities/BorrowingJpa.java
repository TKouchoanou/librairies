package persistence.jpa.entities;

import domain.model.entities.Member;
import domain.model.valueObject.BorrowStatus;
import domain.model.valueObject.ReturnStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingJpa {
    @Id
    @GeneratedValue
    Long id ;
   @Column(name = "member_id",insertable = false,updatable = false)
    Long memberId;

    Long bookId;

    LocalDate pickUpDate;

    LocalDate returnedDate;

    LocalDate effectiveReturnedDate;
    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    BorrowStatus borrowStatus;
    ReturnStatus returnStatus;
}
