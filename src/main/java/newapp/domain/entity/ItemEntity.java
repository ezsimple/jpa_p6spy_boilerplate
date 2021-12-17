package newapp.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "T_ITEM")
public class ItemEntity extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "F_ID")
  private Long id;

  @Column(name = "F_NAME")
  private String name;

  @Column(name = "F_PRICE")
  private BigDecimal price;

}
