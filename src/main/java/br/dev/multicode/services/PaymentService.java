package br.dev.multicode.services;

import br.dev.multicode.models.OrderPaymentMessage;

public interface PaymentService {

  void processPayment(OrderPaymentMessage orderPaymentMessage);

}
