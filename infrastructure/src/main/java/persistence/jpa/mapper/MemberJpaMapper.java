package persistence.jpa.mapper;

import domain.model.entities.Member;
import org.mapstruct.Mapper;
import persistence.jpa.entities.MemberJpa;
@Mapper(componentModel = "spring")
public interface MemberJpaMapper extends GenericJpaMapper<Member, MemberJpa>{
}
