package newapp.domain.repository;

import newapp.domain.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CodeRepository extends JpaRepository<CodeEntity, String>, QuerydslPredicateExecutor<CodeEntity> {
}
