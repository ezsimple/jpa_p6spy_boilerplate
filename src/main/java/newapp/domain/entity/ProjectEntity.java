package newapp.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany
    @JoinColumn(name = "F_USER_ID")
    private List<UserEntity> users = new ArrayList<>(); // 프로젝트원

    @OneToMany
    @JoinColumn(name = "F_COMPANY_NO")
    private List<CompanyEntity> companies = new ArrayList<>(); // 프로젝트 관련 업체

    @JoinColumn(name ="F_PICK_YN")
    private String pickYn; // 프로젝트 선택 여부 - 로그인 사용자는 하나의 프로젝트만 선택할 수 있습니다.

}
