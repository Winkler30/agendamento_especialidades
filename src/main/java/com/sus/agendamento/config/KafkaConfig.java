package com.sus.agendamento.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic agendamentoCriadoTopic() {
        return TopicBuilder.name("agendamento-criado")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic atualizacaoTaxasTopic() {
        return TopicBuilder.name("atualizacao-taxas")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
