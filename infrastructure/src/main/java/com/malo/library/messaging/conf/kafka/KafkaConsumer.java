package com.malo.library.messaging.conf.kafka;

import com.malo.library.avro.UserDto;
import com.malo.library.command.command.member.CreateMemberCommand;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumer {
    public static final String CONSUMER_GROUP_ID = "borrow_members";
    public static final String OFFSET_RESET_CONFIG = "earliest";
    public static final String TRUSTED_PACKAGES = "*";
    @Value("${spring.kafka.bootstrap-servers}")
    String bootStrapServer;

    @Value("${spring.cloud.stream.kafka.binder.producer-properties.schema.registry.url}")
    private String kafkaSchemaRegistryUrl;


    <V> ConsumerFactory<String, V> consumerFactory(Class<V> type) {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OFFSET_RESET_CONFIG);

        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaSchemaRegistryUrl);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        props.put(JsonDeserializer.TRUSTED_PACKAGES, TRUSTED_PACKAGES);
        return new DefaultKafkaConsumerFactory<>(props);
    }
/*
 <V> ConsumerFactory<String,V> consumerFactory(Class<V> type){
        Map<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"borrow_members");
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
       props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(),new JsonDeserializer<>(type));
    }

*/

    <V> ConcurrentKafkaListenerContainerFactory<String, V> concurrentKafkaListenerContainerFactory(Class<V> type) {
        ConcurrentKafkaListenerContainerFactory<String, V> concurrentKafkaListenerContainer = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainer.setConsumerFactory(consumerFactory(type));
        concurrentKafkaListenerContainer.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return concurrentKafkaListenerContainer;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, CreateMemberCommand> createMemberCommandConcurrentKafkaListenerContainerFactory() {
        return concurrentKafkaListenerContainerFactory(CreateMemberCommand.class);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, UserDto> userDTOCommandConcurrentKafkaListenerContainerFactory() {
        return concurrentKafkaListenerContainerFactory(UserDto.class);
    }

    @Bean
    public SchemaRegistryClient schemaRegistryClient() {
        ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
        client.setEndpoint(kafkaSchemaRegistryUrl);
        return client;
    }
}
