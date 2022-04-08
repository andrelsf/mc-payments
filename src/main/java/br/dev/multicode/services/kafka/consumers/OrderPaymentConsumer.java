package br.dev.multicode.services.kafka.consumers;

import br.dev.multicode.models.OrderPaymentMessage;
import br.dev.multicode.services.PaymentService;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

@ApplicationScoped
public class OrderPaymentConsumer {

  private final Logger logger = Logger.getLogger(this.getClass());

  @Inject PaymentService paymentService;

  @Incoming("sec-payment")
  public CompletionStage<Void> receiveOrderPaymentFromKafka(Message<OrderPaymentMessage> orderPaymentMessage)
  {
    var metadata = orderPaymentMessage.getMetadata(IncomingKafkaRecordMetadata.class)
        .orElseThrow();

    OrderPaymentMessage orderPaymentMessageReceived = orderPaymentMessage.getPayload();
    logger.infof("%s - Got a order message to payment: orderId=%s", metadata.getTopic(),
        orderPaymentMessageReceived.getOrderId());
    paymentService.processPayment(orderPaymentMessageReceived);

    return orderPaymentMessage.ack();
  }

}
