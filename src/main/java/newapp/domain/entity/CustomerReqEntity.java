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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_NO")
    Long no;                       // 요청번호

    @ManyToOne
    KindEntity kindEntity;         // 문제점분류

    @ManyToOne
    CustomerEntity customerEntity; // 고객정보

    @ManyToOne
    ProgressEntity progressEntity; // 진행정보

    @ManyToOne
    UserEntity userEntity;         // 접수자정보

}
