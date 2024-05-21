package com.malo.library.jpa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blocking")
public class BlockingJpa extends PeriodicJpa {
    @Id
    public Long id;

    @Column(name ="member_id" ,insertable = false,updatable = false)
    public Long memberId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    MemberJpa member;
}
