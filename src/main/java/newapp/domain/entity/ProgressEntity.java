package newapp.domain.entity;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_PROGRESS")
public class ProgressEntity {

    // 진행상태

    @Id
    @Column(name = "F_STS_NO")
    Long stsNo; // 진행상태 번호

    @Column(name = "F_STS_NM")
    String stsNm; // 진행상태명

    @NotNull
    @Column(name = "F_USE_YN", length = 1, columnDefinition = "char(1) default 'Y'")
    private String useYn;        // 사용여부
}
