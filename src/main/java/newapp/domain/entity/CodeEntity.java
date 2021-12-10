package newapp.domain.entity;

import lombok.Getter;
import lombok.Setter;
import newapp.domain.entity.pk.CodeEntityPk;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "T_CODE")
@IdClass(CodeEntityPk.class)
public class CodeEntity extends BaseEntity {

  @Id
  @Column(name = "F_GID", length = 3)
  private String gid; // groupId(3)

  @Id
  @Column(name = "F_CID", length = 3)
  private String cid; // codeId(3)

  @Column(name = "F_CNAME", nullable = false)
  private String cname; // codeName

  @Column(name = "F_ORDER", nullable = false)
  @ColumnDefault("0")
  private int order;    // 정렬순서

}
