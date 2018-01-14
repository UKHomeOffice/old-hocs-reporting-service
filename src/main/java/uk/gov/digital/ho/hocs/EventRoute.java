package uk.gov.digital.ho.hocs;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws.sqs.SqsConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.digital.ho.hocs.model.Event;

@Component
public class EventRoute extends RouteBuilder {

    private final EventService eventService;
    private final String commandQueue;
    private final int maximumRedeliveries;
    private final int redeliveryDelay;
    private final int backOffMultiplier;

    @Autowired
    public EventRoute(EventService eventService,
                      @Value("${case.queue.command.queue}") String commandQueue,
                      @Value("${case.queue.maximumRedeliveries}") int maximumRedeliveries,
                      @Value("${case.queue.redeliveryDelay}") int redeliveryDelay,
                      @Value("${case.queue.backOffMultiplier}") int backOffMultiplier) {

        this.eventService = eventService;
        this.commandQueue = commandQueue;
        this.maximumRedeliveries = maximumRedeliveries;
        this.redeliveryDelay = redeliveryDelay;
        this.backOffMultiplier = backOffMultiplier;
    }

    @Override
    public void configure() throws Exception {

        onException(JsonMappingException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "Unable to process the message as a command: ${body}");

        onException(InvalidPayloadException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "payload exception: ${body}");

        onException(RuntimeException.class)
                .log(LoggingLevel.ERROR, "Failed to run the command after configured back-off.")
                .useOriginalMessage() // this is config for moving the message to a DLQ
                .retryAttemptedLogLevel(LoggingLevel.WARN)
                .maximumRedeliveries(maximumRedeliveries)
                .redeliveryDelay(redeliveryDelay)
                .backOffMultiplier(backOffMultiplier)
                .asyncDelayedRedelivery()
                .logRetryStackTrace(true);

        from(commandQueue)
                .setProperty(SqsConstants.RECEIPT_HANDLE, header(SqsConstants.RECEIPT_HANDLE))
                .log("Command received: ${body}")
                .unmarshal().json(JsonLibrary.Jackson, Event.class)
                .log("Case Command unmarshalled")
                .bean(eventService, "createEvent")
                .log("Command processed")
                .setHeader(SqsConstants.RECEIPT_HANDLE, exchangeProperty(SqsConstants.RECEIPT_HANDLE));
    }

}
