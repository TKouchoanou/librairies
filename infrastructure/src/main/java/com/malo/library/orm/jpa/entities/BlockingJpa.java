package com.malo.library.orm.jpa.entities;

import com.malo.library.orm.jpa.Identifiable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "member_id",insertable = false,updatable = false)
    MemberJpa member;
}
