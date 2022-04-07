package br.dev.multicode.services;

import br.dev.multicode.models.OrderProcessingStatus;

public interface NotificationService {

  void doNotification(OrderProcessingStatus orderProcessingStatus);

}
