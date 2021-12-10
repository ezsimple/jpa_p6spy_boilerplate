package newapp.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "T_LANG")
public class LangEntity extends BaseEntity {

  @Id
  @Column(name = "F_ID", length = 6)
  private String id; // Id(6 = gid+cid)

  @Column(name = "F_NAME", nullable = false)
  private String name; // codeName

  @Column(name = "F_LANG", nullable = false)
  @ColumnDefault("KO")
  private String lang; // lang Code

}
