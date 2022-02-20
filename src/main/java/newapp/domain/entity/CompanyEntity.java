package newapp.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_COMPANY")
public class CompanyEntity extends BaseEntity {

    // 회사정보

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_NO")
    Long no;        // 회사번호

    @Column(name = "F_NAME")
    String name;    // 회사명

    @Column(name = "F_MEMO")
    String memo;    // 설명

}
