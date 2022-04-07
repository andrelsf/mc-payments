package br.dev.multicode.repositories;

import br.dev.multicode.entities.Payment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {

  public Payment findPaymentByOrderId(String orderId)
  {
    return this.find("order_id = :orderId", Parameters.with("orderId", orderId))
        .firstResultOptional()
        .orElseThrow();
  }

  @Transactional
  public Payment save(Payment payment)
  {
    this.persistAndFlush(payment);
    return payment;
  }
}
