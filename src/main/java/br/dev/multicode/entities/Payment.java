package br.dev.multicode.entities;

import static org.apache.commons.lang3.RandomStringUtils.random;

import br.dev.multicode.enums.OrderStatus;
import br.dev.multicode.enums.TypePayment;
import br.dev.multicode.models.OrderPaymentMessage;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends PanacheEntityBase {

  @Id
  @Column(name = "payment_id", length = 37, nullable = false)
  private String id;

  @Column(name = "order_id", length = 37, nullable = false)
  private String orderId;

  @Column(name = "user_id", length = 37, nullable = false)
  private String userId;

  @Enumerated(EnumType.STRING)
  @Column(length = 30, nullable = false)
  private OrderStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "type_payment", length = 30, nullable = false)
  private TypePayment typePayment;

  @Column(name = "transaction_id", length = 37, nullable = false)
  private String transactionId;

  @Column(name = "authorization_code", length = 30, nullable = false)
  private String authorizationCode;

  @Column(name = "amount_paid", nullable = false)
  private BigDecimal amountPaid;

  @CreationTimestamp
  @Column(name = "received_at", nullable = false)
  private ZonedDateTime receivedAt;

  @PrePersist
  private void prePersist()
  {
    this.id = UUID.randomUUID().toString();
  }

  public static Payment of(OrderPaymentMessage orderPaymentMessage) {
    return Payment.builder()
        .orderId(orderPaymentMessage.getOrderId().toString())
        .userId(orderPaymentMessage.getUserId().toString())
        .status(OrderStatus.PAYMENT_DONE) // random
        .typePayment(TypePayment.CREDIT_CARD)
        .transactionId(UUID.randomUUID().toString())
        .authorizationCode(random(30, true, true))
        .amountPaid(orderPaymentMessage.getPrice())
        .build();
  }
}
