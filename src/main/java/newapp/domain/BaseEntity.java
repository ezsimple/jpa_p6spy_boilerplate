package newapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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

  @Column(name = "F_USE_YN", length = 1)
  @ColumnDefault("Y")
  private String useYn; // 사용여부

  @Column(name = "F_DEL_YN", length = 1)
  @ColumnDefault("N")
  private String delYn; // 삭제여부

  @CreatedBy
	@Column(name="F_REG_ID", updatable=false)
	private String regId;       // 등록자ID

	@LastModifiedBy
	@Column(name="F_MOD_ID", insertable=false)
	private String modId;       // 수정자ID

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@CreatedDate
	@Column(name="F_REG_DT", updatable=false)
	private LocalDateTime regDt;    // 등록 일시

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@LastModifiedDate
	@Column(name="F_MOD_DT", insertable=false)
	private LocalDateTime modDt;    // 수정 일시
}
