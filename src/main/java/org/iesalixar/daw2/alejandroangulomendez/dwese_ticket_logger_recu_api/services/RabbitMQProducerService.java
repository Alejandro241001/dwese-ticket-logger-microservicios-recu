package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.config.RabbitMQConfig.QUEUE_NAME;

@Service
public class RabbitMQProducerService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducerService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
        System.out.println("Mensaje enviado a RabbitMQ: " + message);
    }
}