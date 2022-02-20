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
@Table(name = "T_CUSTOMER")
public class CustomerEntity {

    // 고객정보

    @Id
    @Column(name = "F_NO")
    Long no;        // 고객번호

    @Column(name = "F_NAME")
    String name;    // 고객명

    @Column(name = "F_EMAIL")
    String email;   // 이메일

    @Column(name = "F_PHONE_NO")
    String phoneNo; // 전화번호

    @ManyToOne
    CompanyEntity componyEntity; // 소속회사

    @NotNull
    @Column(name = "F_USE_YN", length = 1, columnDefinition = "char(1) default 'Y'")
    private String useYn;        // 사용여부
}
