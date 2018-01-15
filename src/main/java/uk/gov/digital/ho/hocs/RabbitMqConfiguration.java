package uk.gov.digital.ho.hocs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    EventService eventService;

    @Value("${hocs.rabbitmq.queue}")
    String queueName;

    @Value("${hocs.rabbitmq.exchange}")
    String exchange;

    @Value("${hocs.rabbitmq.routingkey}")
    String routingKey;

    @Value("${hocs.rabbitmq.routingkey.binding}")
    String routingKeyBinding;

    @Bean
    public TopicExchange receiverExchange() {
        return new TopicExchange("eventExchange");
    }

    @Bean
    public Queue eventReceivingQueue() {
        if (queueName == null) {
            throw new IllegalStateException("No queue to listen to! Please specify the name of the queue to listen to with the property 'subscriber.queue'");
        }
        return new Queue(queueName);
    }

    @Bean
    public Binding binding(Queue eventReceivingQueue, TopicExchange receiverExchange) {
        if (routingKey == null) {
            throw new IllegalStateException("No events to listen to! Please specify the routing key for the events to listen to with the property 'subscriber.routingKey' (see EventPublisher for available routing keys).");
        }
        return BindingBuilder
                .bind(eventReceivingQueue)
                .to(receiverExchange)
                .with(routingKeyBinding);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(EventResource eventSubscriber) {
        return new MessageListenerAdapter(eventSubscriber, "postEvent");
    }

    @Bean
    public EventService eventReceiver() {
        return eventService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("Subscribing to events matching key '{}' from queue '{}'", routingKeyBinding, queueName);
    }
}

