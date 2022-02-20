package newapp.domain.repository;

import newapp.domain.entity.KindEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface KindRepository extends JpaRepository<KindEntity, Long>, QuerydslPredicateExecutor<KindEntity> {
}
