package newapp.domain.repository;

import newapp.domain.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<CodeEntity, String>, QuerydslPredicateExecutor<CodeEntity> {
    Optional<CodeEntity> findByCode6(String code6);
}
