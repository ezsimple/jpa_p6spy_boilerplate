package newapp.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_CUSTOMER_REQ")
public class CustomerReqEntity extends BaseEntity {

    // 고객요청사항

    @Id
    @Column(name = "F_NO")
    private Long no;                       // 요청번호

    @Column(name = "F_KIND_CD", length = 6)
    private String kindCd;                 // 문제점분류

    @ManyToOne
    @JoinColumn(name = "F_CUSTOMER_NO")
    private CustomerEntity customerEntity; // 고객정보

    @Column(name = "F_PROGRESS_CD", length = 6)
    private String progressCd;             // 진행정보

    @ManyToOne
    @JoinColumn(name = "F_USER_ID")
    private UserEntity userEntity;         // 접수자정보

}
