package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "tickets.queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }
}