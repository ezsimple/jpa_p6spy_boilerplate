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
@Table(name = "T_COMPANY")
public class CompanyEntity {

    @Id
    @Column(name = "F_NO")
    private Long no;        // 회사번호

    @Column(name = "F_NAME")
    private String name;    // 회사명

    @Column(name = "F_MEMO")
    private String memo;    // 설명

    @ManyToOne
    @JoinColumn(name = "F_PROJ_NO")
    private ProjectEntity projectEntity;

    @NotNull
    @Column(name = "F_USE_YN", length = 1, columnDefinition = "char(1) default 'Y'")
    private String useYn;   // 사용여부

}
