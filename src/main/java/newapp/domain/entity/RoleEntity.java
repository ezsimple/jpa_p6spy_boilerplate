package newapp.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_ROLE")
public class RoleEntity {

    // 권한정보

    @Id
    @Column(name = "F_ROLE_NO")
    Long roleNo; // 권한번호

    @Column(name = "F_ROLE_NM")
    String roleNm; // 권한명

    @ManyToOne
    UserEntity userEntity;

}
