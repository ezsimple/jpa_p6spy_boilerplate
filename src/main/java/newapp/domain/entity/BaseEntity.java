package newapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public class BaseEntity implements Serializable {

  // @Column 대신 검증 어노테이션을 쓰려고 한다면, @NotNull 외의 다른 것을 쓰지 않도록 주의!!
  // nullable = false 보다, @NotNull을 추천

  @NotNull
  @Column(name = "F_USE_YN", length = 1)
  @ColumnDefault("Y")
  private String useYn; // 사용여부

  @NotNull
  @Column(name = "F_DEL_YN", length = 1)
  @ColumnDefault("N")
  private String delYn; // 삭제여부

  @NotNull
  @CreatedBy
	@Column(name="F_REG_ID", updatable=false)
	private String regId;       // 등록자ID

	@LastModifiedBy
	@Column(name="F_MOD_ID", insertable=false)
	private String modId;       // 수정자ID

  @NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@CreatedDate
	@Column(name="F_REG_DT", updatable=false)
	private LocalDateTime regDt;    // 등록 일시

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@LastModifiedDate
	@Column(name="F_MOD_DT", insertable=false)
	private LocalDateTime modDt;    // 수정 일시
}
