package br.dev.multicode.services.impl;

import br.dev.multicode.models.OrderProcessingStatus;
import br.dev.multicode.services.NotificationService;
import br.dev.multicode.services.kafka.producers.PaymentResponseStatusProducer;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class NotificationServiceImpl implements NotificationService {

  @Inject PaymentResponseStatusProducer producer;

  @Override
  public void doNotification(OrderProcessingStatus orderProcessingStatus)
  {
    Uni.createFrom()
      .item(orderProcessingStatus)
      .emitOn(Infrastructure.getDefaultWorkerPool())
      .subscribe()
      .with(producer::sendToKafka, Throwable::new);
  }
}
