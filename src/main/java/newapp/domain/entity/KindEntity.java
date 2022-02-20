package newapp.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "T_KIND")
public class KindEntity extends BaseEntity {

    // 문제점 분류

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_KIND_NO")
    Long kindNo; // 분류번호

    @Column(name = "F_KIND_NM")
    String kindNm; // 분류명
}
