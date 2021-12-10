package newapp.domain.entity;

import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "T_SCHOOL")
public class SchoolEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "F_NAME")
  String name;
}
