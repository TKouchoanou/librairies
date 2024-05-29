package com.malo.library.messaging.consumers.kafka;

import com.malo.library.avro.UserDto;
import com.malo.library.command.CommandManager;
import com.malo.library.messaging.consumers.kafka.mapper.MemberAvroMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class MemberConsumer {
    CommandManager commandManager;

    MemberAvroMapper memberAvroMapper;

    public MemberConsumer(CommandManager commandManager, MemberAvroMapper memberAvroMapper) {
        this.commandManager = commandManager;
        this.memberAvroMapper = memberAvroMapper;
    }

    @KafkaListener(topics = "${kafka.user.topic}",containerFactory = "userDTOCommandConcurrentKafkaListenerContainerFactory")
    void createUSer(UserDto userDTO, Acknowledgment acknowledgment){
        this.commandManager.process(memberAvroMapper.convertToDto(userDTO));
        acknowledgment.acknowledge();
     }
}
