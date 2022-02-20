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
@Table(name = "T_USER")
public class UserEntity {

    // 시스템 사용자 정보

    @Id
    @Column(name = "F_USER_ID")
    String userId; // 사용자ID

    @Column(name = "F_USER_PW")
    String userPw; // 암호

    @Column(name = "F_USER_NM")
    String userNm;   // 사용자명

    @Column(name = "F_USER_EMAIL")
    String userEmail;  // 이메일

    @NotNull
    @Column(name = "F_USE_YN", length = 1, columnDefinition = "char(1) default 'Y'")
    private String useYn;        // 사용여부

}
