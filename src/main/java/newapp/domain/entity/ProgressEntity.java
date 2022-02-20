package newapp.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_PROGRESS")
public class ProgressEntity extends BaseEntity {

    // 진행상태

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_STS_NO")
    Long stsNo; // 진행상태 번호

    @Column(name = "F_STS_NM")
    String stsNm; // 진행상태명
}
