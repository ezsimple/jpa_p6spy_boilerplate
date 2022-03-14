package newapp.domain.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@DynamicInsert
@DynamicUpdate
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class BaseEntity implements Serializable {

  // @Column 대신 검증 어노테이션을 쓰려고 한다면, @NotNull 외의 다른 것을 쓰지 않도록 주의!!
  // nullable = false 보다, @NotNull을 추천
  // @ColumnDefault("Y") -> auto-ddl 시에만 동작하는 어노테이션입니다.
  // @DynamicInsert : insert 시 null 인 필드 제외
  // @DynamicUpdate : update 시 null 인 필드 제외
  // @Column(name = "F_USE_YN", length = 1, columnDefinition = "char(1) default 'Y'")
  // @Temporal(TemporalType.TIMESTAMP) // @Temporal should only be set on a java.util.Date or java.util.Calendar property

  @NotNull
  @Column(name = "F_USE_YN", length = 1, columnDefinition = "char(1) default 'Y'")
  private String useYn;        // 사용여부

  /** Audit 기능과 함께 연동합니다. */
  @CreatedBy
  @Column(name = "F_REG_ID", columnDefinition = "varchar(255) default 'system'")
  private String regId;        // 등록자ID

  /** Audit 기능과 함께 연동합니다. */
  @LastModifiedBy
  @Column(name = "F_MOD_ID", columnDefinition = "varchar(255) default 'system'")
  private String modId;        // 수정자ID

  @CreationTimestamp
  @Column(name = "F_REG_DT", columnDefinition = "datetime default current_timestamp")
  private LocalDateTime regDt; // 등록 일시

  @UpdateTimestamp
  @Column(name = "F_MOD_DT", columnDefinition = "datetime default current_timestamp on update current_timestamp")
  private LocalDateTime modDt; // 수정 일시

}
