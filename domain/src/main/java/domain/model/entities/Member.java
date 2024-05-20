package domain.model.entities;

import domain.model.valueObject.MemberStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    Long id;
    MemberStatus status;

}
