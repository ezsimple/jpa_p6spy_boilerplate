package newapp.domain.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import newapp.domain.entity.pk.CodeEntityPk;

import javax.persistence.*;
import javax.validation.constraints.Size;

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
