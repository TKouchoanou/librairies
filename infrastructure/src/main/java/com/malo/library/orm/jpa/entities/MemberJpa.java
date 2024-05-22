package com.malo.library.orm.jpa.entities;

import com.malo.library.domain.model.valueObject.MemberAccountStatus;
import com.malo.library.orm.jpa.Identifiable;
import jakarta.persistence.*;
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
@Table(name = "member")
public class MemberJpa implements Identifiable<Long> {
    @Id
    Long id;
    MemberAccountStatus status;
    @OneToMany(mappedBy = "member")
    List<BlockingJpa> blockings;

}
