package newapp.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Setter
@Getter
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "T_SHOP")
public class ShopEntity extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "F_ID")
  private Long id;

  @Column(name = "F_NAME")
  private String name;

  @Column(name = "F_ADDRESS")
  private String address;

//  public ShopEntity(Long id, String name, String address) {
//    this.id = id;
//    this.name = name;
//    this.address = address;
//  }
}
