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
@Table(name = "T_PROJECT")
public class ProjectEntity extends BaseEntity {

    @Id
    @Column(name = "F_PROJ_NO")
    private Long projNo;   // 프로젝트 번호

    @Column(name = "F_PROJ_NM")
    private String projNm; // 프로젝트 명

    @ManyToOne
    @JoinColumn(name = "F_USER_ID")
    private UserEntity userEntity; // 프로젝트원

    @ManyToOne
    @JoinColumn(name = "F_COMPANY_NO")
    private CompanyEntity companyEntity; // 프로젝트 관련 업체

}
