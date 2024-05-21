package com.malo.library.domain.model.entities;

import com.malo.library.domain.model.valueObject.MemberAccountStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
public class Member {
    Long id;
    MemberAccountStatus status;

    @Getter(AccessLevel.NONE)
    final List<Blocking> blockingList;

    public boolean isNotActive() {
        return !status.equals(MemberAccountStatus.ACTIVE);
    }

    public boolean isBlocked() {
        return Objects.nonNull(blockingList) && !Objects.equals(0, blockingList.stream().filter(Blocking::coversToday).count());
    }

}
