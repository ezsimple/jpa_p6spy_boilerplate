package newapp.domain.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "T_LANG")
public class LangEntity extends BaseEntity {

  @Id
  @Column(name = "F_ID", length = 6)
  @GeneratedValue
  private String id; // Id(6 = t_code.gid + t_code.cid)

  @NotNull
  @Column(name = "F_NAME")
  private String name; // codeName

  @NotNull
  @Column(name = "F_LANG", columnDefinition = "char(2) default 'KO'")
  private String lang; // lang Code

}
