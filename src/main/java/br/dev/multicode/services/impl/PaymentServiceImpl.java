package br.dev.multicode.services.impl;

import br.dev.multicode.entities.Payment;
import br.dev.multicode.models.OrderPaymentMessage;
import br.dev.multicode.models.OrderProcessingStatus;
import br.dev.multicode.repositories.PaymentRepository;
import br.dev.multicode.services.NotificationService;
import br.dev.multicode.services.PaymentService;
import br.dev.multicode.services.kafka.producers.PaymentResponseStatusProducer;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PaymentServiceImpl implements PaymentService {

  @Inject PaymentRepository repository;
  @Inject NotificationService notificationService;
  @Inject PaymentResponseStatusProducer paymentResponseStatusProducer;

  @Override
  public void processPayment(OrderPaymentMessage orderPaymentMessage)
  {
    final Payment payment = repository.save(Payment.of(orderPaymentMessage));
    notificationService.doNotification(OrderProcessingStatus.of(orderPaymentMessage, payment.getStatus()),
        paymentResponseStatusProducer);
  }
}
