package newapp.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * JPA CRUD 테스트용 테이블
 */
@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Table(name = "T_CRUD")
public class CrudEntity {

    @Id
    @Column(name = "F_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "F_DATA")
    private String data;
}
