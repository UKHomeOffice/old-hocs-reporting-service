package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
@Profile("rabbitmq")
public class RabbitMqConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${hocs.rabbitmq.exchange}")
    String exchange;

    @Value("${hocs.rabbitmq.routingkey}")
    String routingKey;

    @Value("${hocs.rabbitmq.routingkey.binding}")
    String routingKeyBinding;


    @Bean
    public Queue eventReceivingQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public TopicExchange receiverExchange() {
        if (exchange == null) {
            throw new IllegalStateException("No exchange to listen to!");
        }
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(Queue eventReceivingQueue, TopicExchange receiverExchange) {
        if (routingKeyBinding == null) {
            throw new IllegalStateException("No events to listen to!");
        }
        return BindingBuilder
                .bind(eventReceivingQueue)
                .to(receiverExchange)
                .with(routingKeyBinding);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("Subscribing to events matching key '{}'", routingKeyBinding);
    }
}

