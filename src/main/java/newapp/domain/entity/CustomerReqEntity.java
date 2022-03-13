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

    @Id
    @Column(name = "F_NO")
    private Long no;                       // 요청번호

    @Column(name = "F_KIND_CD", length = 6)
    private String kindCd;                 // 분류코드

    @Column(name = "F_PROGRESS_CD", length = 6)
    private String progressCd;             // 진행정보코드

    @ManyToOne
    @JoinColumn(name = "F_REQ_COMPANY_NO")
    private CompanyEntity companyEntity;   //  요청업체정보

    @Column(name = "F_REQ_USER_NM")
    private String reqUserNm;              // 요청고객명

    @Column(name = "F_REQ_USER_EMAIL")
    private String reqUserEmail;           // 요청고객 이메일

    @Column(name = "F_REQ_USER_PHONE_NO")
    private String reqUserPhoneNo;         // 요청고객 전화번호

    @ManyToOne
    @JoinColumn(name = "F_USER_ID")
    private UserEntity userEntity;         // 접수자정보 (현사용자 정보)

    @Column(name = "F_REQ_CONTENT")        // 요청내용
    private String reqContent;

    @Column(name = "F_RES_CONTENT")        // 응답내용
    private String resContent;
}
