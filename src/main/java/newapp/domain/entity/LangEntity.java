package newapp.domain.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "T_LANG")
public class LangEntity {

  @Id
  @Column(name = "F_LANG", columnDefinition = "char(2) default 'KO'", length = 2)
  private String lang; // lang Code

  @NotNull
  @Column(name = "F_NAME")
  private String name; // codeName

}
