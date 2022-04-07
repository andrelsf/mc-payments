package br.dev.multicode.entities;

import br.dev.multicode.enums.OrderStatus;
import br.dev.multicode.enums.TypePayment;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @Column(name = "payment_id", length = 37, nullable = false)
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
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
  private ZonedDateTime received_at;

}
