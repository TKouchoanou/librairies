package com.malo.library.messaging.consumers.kafka.mapper;

import com.malo.library.command.command.member.CreateMemberCommand;
import com.malo.library.avro.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberAvroMapper  extends AvroMapper<CreateMemberCommand,UserDto>{
}
