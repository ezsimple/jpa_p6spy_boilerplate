package newapp.domain.repository;

import newapp.domain.entity.UserRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshTokenEntity, String>, QuerydslPredicateExecutor<UserRefreshTokenEntity> {
    UserRefreshTokenEntity findByUserId(String userId);
    UserRefreshTokenEntity findByUserIdAndRefreshToken(String userId, String refreshToken);
}
