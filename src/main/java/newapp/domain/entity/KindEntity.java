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
@Table(name = "T_KIND")
public class KindEntity {

    // 문제점 분류

    @Id
    @Column(name = "F_KIND_NO")
    Long kindNo; // 분류번호

    @Column(name = "F_KIND_NM")
    String kindNm; // 분류명

    @NotNull
    @Column(name = "F_USE_YN", length = 1, columnDefinition = "char(1) default 'Y'")
    private String useYn;        // 사용여부
}
