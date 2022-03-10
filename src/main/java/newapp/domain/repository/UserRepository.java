package newapp.domain.repository;

import newapp.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<UserEntity, String>, QuerydslPredicateExecutor<UserEntity> {
    UserEntity findByUserId(String userId);
}
