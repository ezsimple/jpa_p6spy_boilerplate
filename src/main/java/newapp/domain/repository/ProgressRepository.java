package newapp.domain.repository;

import newapp.domain.entity.ProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProgressRepository extends JpaRepository<ProgressEntity, Long>, QuerydslPredicateExecutor<ProgressEntity> {
}
