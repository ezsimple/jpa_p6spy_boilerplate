package newapp.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_USER")
public class UserEntity extends BaseEntity {

    // 시스템 사용자 정보

    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "F_USER_ID")
    String userId; // 사용자ID

    @Column(name = "F_USER_PW")
    String userPw; // 암호

    @Column(name = "F_USER_NM")
    String userNm;   // 사용자명

    @Column(name = "F_USER_EMAIL")
    String userEmail;  // 이메일

}
