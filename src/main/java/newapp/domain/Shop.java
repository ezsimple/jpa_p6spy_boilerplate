package newapp.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "T_SHOP")
public class Shop {

  @Id
  @GeneratedValue
  @Column(name = "F_ID")
  private Long id;

  @Column(name = "F_NAME")
  private String name;

  @Column(name = "F_ADDRESS")
  private String address;

}
