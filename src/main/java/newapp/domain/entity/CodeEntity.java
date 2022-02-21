package newapp.domain.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import newapp.domain.entity.pk.CodeEntityPk;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Table(name = "T_CODE")
@IdClass(CodeEntityPk.class)
public class CodeEntity {

  @Id
  @Column(name = "F_GID", length = 3)
  private String gid; // groupId(3)

  @NotNull
  @Column(name = "F_GNAME")
  private String gname; // groupName

  @Id
  @Column(name = "F_CID", length = 3)
  private String cid; // codeId(3)

  @NotNull
  @Column(name = "F_CNAME")
  private String cname; // codeName

  @NotNull
  @Column(name = "F_CODE6", length = 6)
  private String code6; // gid(3) || cid(3)

  @NotNull
  @Column(name = "F_ORDER_NO")
  @ColumnDefault("0")
  private Long orderNo;    // 정렬순서

  @NotNull
  @Column(name = "F_USE_YN", length = 1, columnDefinition = "char(1) default 'Y'")
  private String useYn;        // 사용여부

}
