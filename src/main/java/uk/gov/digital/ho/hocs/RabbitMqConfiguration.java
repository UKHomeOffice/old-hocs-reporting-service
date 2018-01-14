package uk.gov.digital.ho.hocs;

import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
@Slf4j
@Profile("rabbitmq")
public class RabbitMqConfiguration {

    @Bean
    public ConnectionFactory rabbitMqClient(@Value("${amqp.connection.factory.uri}") String queueConnectionFactoryUri,
                                            @Value("${rabbit.mq.username}") String username,
                                            @Value("${rabbit.mq.password}") String password) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {


        if (queueConnectionFactoryUri == null || queueConnectionFactoryUri.equals("")) {
            throw new BeanCreationException("Failed to create RabbitMQ client bean. Need non-blank value for queue connection factory uri: " + queueConnectionFactoryUri);
        }

        if (username == null || username.equals("")) {
            throw new BeanCreationException("Failed to create RabbitMQ client bean. Need non-blank values for username: " + username);
        }

        if (password == null && password.equals("")) {
            throw new BeanCreationException("Failed to create RabbitMQ client bean. Need non-blank values for password");
        }

        log.info("Queue Connection Factory {}", queueConnectionFactoryUri);

        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri(queueConnectionFactoryUri);
        factory.setRequestedHeartbeat(10);
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(30000); // In case of broken connection, try again every 30 seconds
        factory.setTopologyRecoveryEnabled(true);
        factory.setUsername(username);
        factory.setPassword(password);

        return factory;

    }
}
