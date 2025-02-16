package com.malo.library.persistence.jpa.entities;

import com.malo.library.persistence.jpa.Identifiable;
import com.malo.library.persistence.jpa.entities.abstractEntity.PeriodicJpa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "blocking")
public class BlockingJpa extends PeriodicJpa implements Identifiable<Long> {
    @Id
    @GeneratedValue
    public Long id;

    @Column(name ="member_id")
    public Long memberId;
    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id",insertable = false,updatable = false)
    MemberJpa member;
}
