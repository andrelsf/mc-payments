package br.dev.multicode.services.kafka.producers;

import br.dev.multicode.models.OrderProcessingStatus;
import io.smallrye.mutiny.Uni;
import java.util.concurrent.CompletableFuture;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

@ApplicationScoped
public class PaymentResponseStatusProducer {

  private final Logger logger = Logger.getLogger(this.getClass());

  @Inject
  @Channel("sec-response-status")
  Emitter<OrderProcessingStatus> emitter;

  public Uni<Void> sendToKafka(final OrderProcessingStatus orderInventoryStatus)
  {
    logger.infof("Start of send message to Kafka topic sec-response-status");

    emitter.send(Message.of(orderInventoryStatus)
        .withAck(() -> {
          logger.infof("Message sent successfully. eventId=%s", orderInventoryStatus.getEventId());
          return CompletableFuture.completedFuture(null);
        })
        .withNack(throwable -> {
          logger.errorf("Message sent failed. ERROR: %s", throwable.getMessage());
          return CompletableFuture.completedFuture(null);
        }));

    return Uni.createFrom().voidItem();
  }
}
