package newapp.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_PROJECT")
public class ProjectEntity extends BaseEntity {

    @Id
    @Column(name = "F_PROJ_NO")
    private Long projNo;   // 프로젝트 번호

    @Column(name = "F_PROJ_NM")
    private String projNm; // 프로젝트 명

    @Column(name ="F_PICK_YN")
    private String pickYn; // 프로젝트 선택 여부 - 로그인 사용자는 하나의 프로젝트만 선택할 수 있습니다.

}
