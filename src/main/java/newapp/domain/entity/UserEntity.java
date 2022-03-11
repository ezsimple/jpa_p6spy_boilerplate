package newapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import newapp.global.oauth2.type.ProviderType;
import newapp.global.oauth2.type.RoleType;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_USER"
        , uniqueConstraints = {
            @UniqueConstraint(columnNames = "F_EMAIL")
        }
)
public class UserEntity {

    @Id
    @Column(name = "F_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "F_ID", length = 64)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(name = "F_NAME", length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

    @JsonIgnore
    @Column(name = "F_PASSWORD", length = 128)
    @NotNull
    @Size(max = 128)
    private String password;

    @Column(name = "F_EMAIL", length = 512)
    @NotNull
    @Size(max = 512)
    private String email;

    @Column(name = "F_EMAIL_VERIFIED_YN", length = 1)
    @NotNull
    @Size(min = 1, max = 1)
    private String emailVerifiedYn;

    @Column(name = "F_PROFILE_IMAGE_URL", length = 512)
    @Nullable
    @Size(max = 512)
    private String profileImageUrl;

    @Column(name = "F_PROVIDER_TYPE", length = 20)
    @Enumerated(EnumType.STRING) //JPA로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지 결정
    @NotNull
    private ProviderType providerType;

    @Column(name = "F_ROLE_TYPE", length = 20)
    @Enumerated(EnumType.STRING) //JPA로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지 결정
    @NotNull
    private RoleType roleType;

    @Column(name = "F_REG_DT")
    @NotNull
    private LocalDateTime regDt;

    @Column(name = "F_MOD_DT")
    @NotNull
    private LocalDateTime modDt;

    public UserEntity (
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max = 100) String username,
            @NotNull @Size(max = 512) String email,
            @NotNull @Size(max = 1) String emailVerifiedYn,
            @Nullable @Size(max = 512) String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType,
            @NotNull LocalDateTime regDt,
            @NotNull LocalDateTime modDt ) {
        this.userId = userId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
        this.regDt = regDt;
        this.modDt = modDt;
    }
}
