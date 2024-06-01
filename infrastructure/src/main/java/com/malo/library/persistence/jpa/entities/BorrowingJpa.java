package com.malo.library.persistence.jpa.entities;

import com.malo.library.domain.model.valueObject.BorrowStatus;
import com.malo.library.domain.model.valueObject.ReturnStatus;
import com.malo.library.persistence.jpa.Identifiable;
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
@Table(name = "borrowing")
public class BorrowingJpa implements Identifiable<Long> {
    @Id
    @GeneratedValue
    Long id ;

    @Column(name = "member_id")
    Long memberId;

    Long bookId;

    LocalDate pickUpDate;

    LocalDate returnedDate;

    LocalDate effectiveReturnedDate;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id",insertable = false,updatable = false)
    MemberJpa member;

    BorrowStatus borrowStatus;
    ReturnStatus returnStatus;
}
