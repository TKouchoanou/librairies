package com.malo.library.jpa.entities;

import com.malo.library.domain.model.valueObject.MemberAccountStatus;
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
public class MemberJpa {
    @Id
    Long id;
    MemberAccountStatus status;
    @OneToMany(mappedBy = "member")
    List<BlockingJpa> blockings;

}
