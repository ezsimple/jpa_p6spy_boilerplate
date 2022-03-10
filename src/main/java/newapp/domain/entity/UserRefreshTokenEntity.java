package newapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "F_USER_REFRESH_TOKEN")
public class UserRefreshTokenEntity {
    @JsonIgnore
    @Id
    @Column(name = "F_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "F_USER_ID", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(name = "F_REFRESH_TOKEN", length = 256)
    @NotNull
    @Size(max = 256)
    private String refreshToken;

    public UserRefreshTokenEntity(
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max = 256) String refreshToken ) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
