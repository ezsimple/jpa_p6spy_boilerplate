package newapp.domain.entity;

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

    // 프로젝트

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_PROJ_NO")
    Long projNo;   // 프로젝트 번호

    @Column(name = "F_PROJ_NM")
    String projNm; // 프로젝트 명

    @ManyToOne
    UserEntity userEntity; // 프로젝트원
}
